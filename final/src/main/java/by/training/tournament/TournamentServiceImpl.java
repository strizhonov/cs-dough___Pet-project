package by.training.tournament;

import by.training.connection.TransactionManager;
import by.training.core.*;
import by.training.game.*;
import by.training.player.PlayerDao;
import by.training.player.PlayerDto;
import by.training.resourse.AppSetting;
import by.training.user.WalletDao;
import by.training.user.WalletDto;
import by.training.util.TournamentUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

@Bean
public class TournamentServiceImpl extends BaseBeanService implements TournamentService {

    private static final Logger LOGGER = LogManager.getLogger(TournamentServiceImpl.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final TournamentDao tournamentDao;
    private final GameDao gameDao;
    private final WalletDao walletDao;
    private final PlayerDao playerDao;
    private final GameServerDao gameServerDao;


    public TournamentServiceImpl(TournamentDao tournamentDao, GameDao gameDao, PlayerDao playerDao, WalletDao walletDao,
                                 GameServerDao gameServerDao, TransactionManager transactionManager) {
        super(transactionManager);
        this.tournamentDao = tournamentDao;
        this.gameDao = gameDao;
        this.playerDao = playerDao;
        this.walletDao = walletDao;
        this.gameServerDao = gameServerDao;
    }


    /**
     * Catch Exception for transaction security reasons
     */
    @Override
    public long create(TournamentDto tournament) throws ServiceException {
        try {
            transactionManager.startTransaction();

            long id = tournamentDao.save(tournament);
            tournament.setId(id);


            takeFromOrganizerBonus(tournament);

            fillWithGames(tournament);

            tournamentDao.update(tournament);

            transactionManager.commitTransaction();

            return id;

        } catch (NotEnoughFundsException e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament creating failed.", e);
            throw e;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament creating failed.", e);
            throw new ServiceException("Tournament creating failed.", e);
        }
    }


    @Override
    public TournamentDto find(long id) throws ServiceException {
        try {

            return tournamentDao.get(id);

        } catch (DaoException e) {
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }


    /**
     * Catch Exception for transaction security reasons
     *
     * @return false if tournament is not empty
     */
    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();

            TournamentDto tournament = find(id);


            if (!tournament.getParticipantsIds().isEmpty()) {
                transactionManager.rollbackTransaction();
                LOGGER.warn("Can't delete tournament with players.");
                return false;
            }

            returnFromOrganiserBonus(tournament);

            transactionManager.commitTransaction();

            return true;
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }


    @Override
    public List<TournamentDto> findAll() throws ServiceException {
        try {

            return tournamentDao.findAll();

        } catch (DaoException e) {
            LOGGER.error("Tournaments retrieving failed.", e);
            throw new ServiceException("Tournaments retrieving failed.", e);
        }
    }


    @Override
    public TournamentDto findByName(String name) throws ServiceException {
        try {

            return tournamentDao.findByName(name);

        } catch (DaoException e) {
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }


    @Override
    public List<TournamentDto> findAllOfPlayer(long playerId) throws ServiceException {
        try {

            return tournamentDao.findAllOfPlayer(playerId);

        } catch (DaoException e) {
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }


    @Override
    public List<TournamentDto> findAllOfOrganizer(long organizerId) throws ServiceException {
        try {

            return tournamentDao.findAllOfOrganizer(organizerId);

        } catch (DaoException e) {
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }


    /**
     * Catch Exception for transaction security reasons
     *
     * @return false if player has already joined tournament
     * or if tournament started
     */
    @Override
    public boolean joinTournament(ParticipantDto participantDto) throws ServiceException {
        try {

            transactionManager.startTransaction();

            if (tournamentDao.isParticipantPresent(participantDto)) {
                LOGGER.warn("Participant already present.");
                transactionManager.rollbackTransaction();
                return false;
            }


            TournamentDto tournament = tournamentDao.get(participantDto.getTournamentId());

            if (tournament.getStatus() != Tournament.TournamentStatus.ONGOING) {
                LOGGER.warn("Tournament has already started.");
                transactionManager.rollbackTransaction();
                return false;
            }


            takeBuyIn(participantDto, tournament);

            tournamentDao.addParticipant(participantDto);

            occupyGameSlot(participantDto);

            if (tournament.getPlayersNumber() == tournament.getParticipantsIds().size()) {
                startTournament(tournament);
            }

            transactionManager.commitTransaction();

            return true;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Failed to join tournament id and player id.", e);
            throw new ServiceException("Failed to join tournament id and player id.", e);
        }
    }


    /**
     * Catch Exception for transaction security reasons
     *
     * @return false if tournament started or if player is not a participant.
     */
    @Override
    public boolean leaveTournament(ParticipantDto participantDto) throws ServiceException {
        try {

            transactionManager.startTransaction();

            if (!tournamentDao.isParticipantPresent(participantDto)) {
                transactionManager.rollbackTransaction();
                LOGGER.warn("Player is not a participant.");
                return false;
            }


            TournamentDto tournament = tournamentDao.get(participantDto.getTournamentId());

            if (tournament.getStatus() != Tournament.TournamentStatus.ONGOING) {
                transactionManager.rollbackTransaction();
                LOGGER.warn("Tournament has already started.");
                return false;
            }


            returnBuyIn(participantDto, tournament);

            tournamentDao.removeParticipant(participantDto);

            releaseGameSlot(participantDto);

            transactionManager.commitTransaction();

            return true;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Failed to join tournament id and player id.", e);
            throw new ServiceException("Failed to join tournament id and player id.", e);
        }
    }


    private void takeFromOrganizerBonus(TournamentDto tournament) throws DaoException, NotEnoughFundsException {

        double fromOrganizerBonus = tournament.getFromOrganizerBonus();

        WalletDto organizerWallet = walletDao.getOfOrganizer(tournament.getOrganizerId());

        if (organizerWallet.getBalance() < fromOrganizerBonus) {
            LOGGER.warn(fromOrganizerBonus + " is exceed balance.");
            throw new NotEnoughFundsException(fromOrganizerBonus + " is exceed balance.");
        }

        organizerWallet.decreaseBalance(fromOrganizerBonus);
        walletDao.update(organizerWallet);

        BufferWallet buffer = (BufferWallet) ApplicationContext.getInstance().get(BufferWallet.class);
        organizerWallet.increaseBalance(fromOrganizerBonus);
        walletDao.update(buffer);

    }


    private void fillWithGames(TournamentDto tournamentDto) throws ServiceException {
        try {
            for (int i = 0; i < tournamentDto.getPlayersNumber() - 1; i++) {
                PlainGameDto plainGameDto = new PlainGameDto.Builder()
                        .tournamentId(tournamentDto.getId())
                        .bracketIndex(i)
                        .build();
                long gameId = gameDao.save(plainGameDto);

                GameServerDto gameServer = createGameServer(gameId);
                gameServerDao.save(gameServer);

                tournamentDto.addGameId(gameId);
            }
        } catch (DaoException e) {
            LOGGER.error("Game creation failed.", e);
            throw new ServiceException("Game creation failed.", e);
        }
    }


    private GameServerDto createGameServer(long gameId) {
        String sPointsToWin = setting.get(AppSetting.SettingName.DEFAULT_POINTS_TO_WIN);
        GameServerModel serverModel = new GameServerModel(Integer.parseInt(sPointsToWin));

        return new GameServerDto.Builder()
                .gameId(gameId)
                .path(serverModel.getPath())
                .build();
    }


    private void returnFromOrganiserBonus(TournamentDto tournament) throws DaoException {
        double fromOrganizerBonus = tournament.getFromOrganizerBonus();

        WalletDto organizerWallet = walletDao.getOfOrganizer(tournament.getOrganizerId());
        organizerWallet.increaseBalance(fromOrganizerBonus);
        walletDao.update(organizerWallet);


        BufferWallet buffer = (BufferWallet) ApplicationContext.getInstance().get(BufferWallet.class);
        organizerWallet.decreaseBalance(fromOrganizerBonus);
        walletDao.update(buffer);
    }


    private void takeBuyIn(ParticipantDto joiningDto, TournamentDto tournament)
            throws DaoException, NotEnoughFundsException {

        double buyIn = tournament.getBuyIn();

        WalletDto playerWallet = walletDao.getOfPlayer(joiningDto.getPlayerId());
        if (playerWallet.getBalance() < buyIn) {
            LOGGER.warn(buyIn + " is exceed balance.");
            throw new NotEnoughFundsException(buyIn + " is exceed balance.");
        }

        playerWallet.decreaseBalance(buyIn);
        walletDao.update(playerWallet);

        BufferWallet appWallet = (BufferWallet) ApplicationContext.getInstance().get(BufferWallet.class);
        appWallet.increaseBalance(buyIn);
        walletDao.update(appWallet);

    }


    private void returnBuyIn(ParticipantDto joiningDto, TournamentDto tournament) throws DaoException {

        double buyIn = tournament.getBuyIn();

        WalletDto playerWallet = walletDao.getOfPlayer(joiningDto.getPlayerId());
        playerWallet.increaseBalance(buyIn);
        walletDao.update(playerWallet);

        BufferWallet appWallet = (BufferWallet) ApplicationContext.getInstance().get(BufferWallet.class);
        appWallet.decreaseBalance(buyIn);
        walletDao.update(appWallet);

    }

    /**
     * Searches an empty player slot from the last game index (@see Game)
     * and sets player there
     */
    private void occupyGameSlot(ParticipantDto dto) throws DaoException {

        List<ComplexGameDto> games = gameDao.findAllComplexOfTournament(dto.getTournamentId());

        ComplexGameDto gameWithEmptySlot = games.stream()
                .filter(ComplexGameDto::hasEmptySlot)
                .max(Comparator.comparingInt(ComplexGameDto::getBracketIndex))
                .orElse(null);

        if (gameWithEmptySlot != null) {
            PlayerDto player = playerDao.get(dto.getPlayerId());
            gameWithEmptySlot.occupyPlayerSlot(player);
            gameDao.update(gameWithEmptySlot);
        } else {

            throw new IllegalStateException("Unexpected absence of empty slots.");
        }
    }


    /**
     * Searches an empty player slot from the last game index (@see Game)
     * and sets player there
     */
    private void releaseGameSlot(ParticipantDto joiningDto) throws DaoException {

        List<ComplexGameDto> games = gameDao.findAllComplexOfTournament(joiningDto.getTournamentId());

        ComplexGameDto playerGame = games.stream()
                .filter(game -> game.getFirstPlayer().getId() == joiningDto.getPlayerId()
                        || game.getFirstPlayer().getId() == joiningDto.getPlayerId())
                .findFirst()
                .orElse(null);


        if (playerGame != null) {

            PlayerDto player = playerDao.get(joiningDto.getPlayerId());
            playerGame.releasePlayerSlot(player);
            gameDao.update(playerGame);

        } else {
            throw new IllegalStateException("Unexpected absence of player's game.");
        }


    }


    private void startTournament(TournamentDto tournament) throws DaoException {
        tournament.setStatus(Tournament.TournamentStatus.ONGOING);
        setGamesStartTime(tournament);

        tournamentDao.update(tournament);
    }


    private void setGamesStartTime(TournamentDto tournament) throws DaoException {

        List<ComplexGameDto> games = gameDao.findAllComplexOfTournament(tournament.getId());

        games.sort(Comparator.comparing(PlainGameDto::getBracketIndex).reversed());

        for (int i = 0; i < games.size(); i++) {

            ComplexGameDto game = games.get(i);

            long interval = Long.parseLong(setting.get(AppSetting.SettingName.GAMES_INTERVAL));

            game.setStartTime(TournamentUtil.getGameAutoTime(interval, i));

            gameDao.update(game);
        }

    }


}
