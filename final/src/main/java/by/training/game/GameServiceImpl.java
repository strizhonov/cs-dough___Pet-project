package by.training.game;

import by.training.connection.TransactionManager;
import by.training.core.*;
import by.training.player.PlainPlayerDto;
import by.training.player.PlayerDao;
import by.training.player.PlayerDto;
import by.training.tournament.*;
import by.training.user.WalletDao;
import by.training.user.WalletDto;
import by.training.util.TournamentUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Bean
public class GameServiceImpl extends BaseBeanService implements GameService {

    private static final Logger LOGGER = LogManager.getLogger(GameServiceImpl.class);
    private final GameDao gameDao;
    private final PlayerDao playerDao;
    private final GameServerDao gameServerDao;
    private final TournamentDao tournamentDao;
    private final WalletDao walletDao;

    public GameServiceImpl(GameDao gameDao,
                           PlayerDao playerDao,
                           GameServerDao gameServerDao,
                           TournamentDao tournamentDao,
                           WalletDao walletDao,
                           TransactionManager transactionManager) {
        super(transactionManager);
        this.gameDao = gameDao;
        this.gameServerDao = gameServerDao;
        this.playerDao = playerDao;
        this.tournamentDao = tournamentDao;
        this.walletDao = walletDao;
    }


    @Override
    public ComplexGameDto find(long id) throws ServiceException {
        try {

            return gameDao.getComplex(id);

        } catch (EntityNotFoundException e) {
            LOGGER.error("Games with id " + id + " not found.", e);
            return null;

        } catch (DaoException e) {
            LOGGER.error("Games retrieving failed.", e);
            throw new ServiceException("Games retrieving failed.", e);
        }
    }


    @Override
    public List<ComplexGameDto> findAll() throws ServiceException {
        try {

            return gameDao.findAllComplex();

        } catch (DaoException e) {
            LOGGER.error("Games retrieving failed.", e);
            throw new ServiceException("Games retrieving failed.", e);
        }

    }


    /**
     * Catch Exception for transaction security reasons
     * <p>
     * Perform emulation of winning round by player.
     *
     * @param gameId     id of game count to increase
     * @param playerSlot index of player within the game (0 or 1).
     * @return true if tournament finished after points had been increased
     */
    @Override
    public boolean increasePlayerPoints(long gameId, int playerSlot) throws ServiceException {
        try {
            transactionManager.startTransaction();

            ComplexGameDto game = gameDao.getComplex(gameId);
            GameServerDto gameServerDto = game.getGameServer();
            GameServerModel server = new GameServerModel(gameServerDto);


            boolean isTournamentFinished = false;
            if (game.isReady()) {

                server.startIfNot();

                boolean isGameToFinish;
                if (playerSlot == 0) {
                    isGameToFinish = server.increaseFirstPlayerPoints();
                    gameServerDto.setPlayerOnePoints(server.getPlayerTwoPoints());

                } else if (playerSlot == 1) {
                    isGameToFinish = server.increaseSecondPlayerPoints();
                    gameServerDto.setPlayerTwoPoints(server.getPlayerTwoPoints());

                } else {
                    throw new ServiceException("Illegal player slot " + playerSlot);
                }

                gameServerDao.update(gameServerDto);

                if (isGameToFinish) {
                    isTournamentFinished = finishGame(game, server);
                }

            }


            transactionManager.commitTransaction();

            return isTournamentFinished;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Count decreasing failed.", e);
            throw new ServiceException("Count decreasing failed.", e);
        }
    }


    @Override
    public List<ComplexGameDto> findAllOfPlayer(long id) throws ServiceException {
        try {

            return gameDao.findAllComplexOfPlayer(id);

        } catch (DaoException e) {
            LOGGER.error("Games retrieving failed.", e);
            throw new ServiceException("Games retrieving failed.", e);
        }
    }


    @Override
    public List<ComplexGameDto> findAllOfTournament(long tournamentId) throws ServiceException {
        try {

            return gameDao.findAllComplex().stream()
                    .filter(game -> game.getTournamentId() == tournamentId)
                    .collect(Collectors.toList());

        } catch (DaoException e) {
            LOGGER.error("Game retrieving failed.", e);
            throw new ServiceException("Game retrieving failed.", e);
        }
    }


    @Override
    public List<ComplexGameDto> findLatest(int maxGameResults) throws ServiceException {
        return findAll().stream()
                .filter(game -> game.getEndTime() != null && game.getEndTime().before(new Date()))
                .sorted(Comparator.comparing(PlainGameDto::getEndTime).reversed())
                .limit(maxGameResults)
                .collect(Collectors.toList());
    }


    /**
     * Catch Exception for transaction security reasons
     */
    @Override
    public List<TournamentPlacement> findTournamentPlacements(TournamentDto tournament) throws ServiceException {
        try {

            transactionManager.startTransaction();

            List<ComplexGameDto> games = findAllOfTournament(tournament.getId());
            games.sort(Comparator.comparingInt(PlainGameDto::getBracketIndex));

            TournamentPrizing prizing = new TournamentPrizing(tournament.getPlayersNumber());

            List<TournamentPlacement> placements = createPlacements(games, prizing, tournament.getPrizePool());
            transactionManager.commitTransaction();

            return placements;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament placements creation failed.", e);
            throw new ServiceException("Tournament placements creation failed.", e);
        }

    }


    private boolean finishGame(ComplexGameDto game, GameServerModel server) throws DaoException, ServiceException {
        game.setEndTime(server.getEndTime());
        gameDao.update(game);

        int gameIndex = game.getBracketIndex();

        if (gameIndex == 0) {

            /* Tournament is finished */
            TournamentDto tournament = tournamentDao.get(game.getTournamentId());
            tournament.setStatus(Tournament.TournamentStatus.FINISHED);
            tournamentDao.update(tournament);

            payRewards(tournament);

            return true;

        } else {

            /* Tournament is continuing */
            int nextGameIndex = TournamentUtil.getNextGameIndex(gameIndex);
            ComplexGameDto nextGame = gameDao.getComplexByBracketIndex(nextGameIndex, game.getTournamentId());

            int slot = nextGame.occupyPlayerSlot(game.getWinner());

            if (slot == 0) {
                gameDao.updateFirstPlayerId(nextGame);
            } else if (slot == 1) {
                gameDao.updateSecondPlayerId(nextGame);
            } else {
                throw new IllegalStateException("Illegal game state, winner not found.");
            }

            return false;
        }
    }


    private void payRewards(TournamentDto tournament) throws ServiceException, DaoException {

        payOrganizer(tournament);

        payParticipants(tournament);

    }


    private void payOrganizer(TournamentDto tournament) throws DaoException {

        double reward = TournamentUtil.calculateOrganizerReward(tournament.getOrganizerRewardPercentage(),
                tournament.getBuyIn(), tournament.getPlayersNumber());

        WalletDto orgWallet = walletDao.getOfOrganizer(tournament.getOrganizerId());
        orgWallet.increaseBalance(reward);
        walletDao.update(orgWallet);

        BufferWallet buffer = (BufferWallet) ApplicationContext.getInstance().get(BufferWallet.class);
        buffer.decreaseBalance(reward);
        walletDao.update(buffer);

    }


    private void payParticipants(TournamentDto tournament) throws ServiceException, DaoException {
        List<TournamentPlacement> placements = findTournamentPlacements(tournament);

        /* Pay placements one by one */
        for (TournamentPlacement placement : placements) {

            List<PlayerDto> players = placement.getPlayersOnPosition();
            int size = players.size();
            if (size > 1) {
                LOGGER.error("Cannot perform paying process. Placement has more than one player.");
                throw new ServiceException("Cannot perform paying process. Placement has more than one player.");
            }

            if (size == 0) {
                continue;
            }

            PlayerDto player = players.get(0);
            double prize = placement.getPrize();
            player.increaseTotalWon(prize);
            playerDao.update(player);

            WalletDto wallet = walletDao.getOfPlayer(player.getId());
            wallet.increaseBalance(prize);
            walletDao.update(wallet);

            BufferWallet buffer = (BufferWallet) ApplicationContext.getInstance().get(BufferWallet.class);
            buffer.decreaseBalance(prize);
            walletDao.update(buffer);

        }
    }


    private List<TournamentPlacement> createPlacements(
            List<ComplexGameDto> games, TournamentPrizing prizing, double prizePool) throws DaoException {

        List<TournamentPlacement> placements = new ArrayList<>();

        for (int gameIndex = games.size() - 1; gameIndex >= 0; gameIndex--) {

            // Define placement
            int placementIndex = gameIndex + 2;
            TournamentPlacement placement = new TournamentPlacement(placementIndex);


            // Setting placement prize for participant
            placement.setPrize(prizing.getPrizeRate(placementIndex) * prizePool);


            // Setting placement prize for participant
            fillWithPlayers(placement, games, gameIndex);


            placements.add(placement);


            // Setting winner placement
            if (gameIndex == 0) {

                TournamentPlacement winner = getWinnerPlacement(prizing, prizePool, games.get(gameIndex));
                placements.add(winner);
            }

        }

        return placements;
    }


    private TournamentPlacement getWinnerPlacement(TournamentPrizing prizing, double prizePool, ComplexGameDto game)
            throws DaoException {

        TournamentPlacement winnerPlacement = new TournamentPlacement(1);
        winnerPlacement.setPrize(prizing.getPrizeRate(1) * prizePool);

        PlainPlayerDto winner = game.getWinner();

        if (winner != null) {
            winnerPlacement.addPlayerId(playerDao.get(game.getWinner().getId()));
            winnerPlacement.setFinished(true);
        }


        return winnerPlacement;
    }


    private void fillWithPlayers(TournamentPlacement placement, List<ComplexGameDto> games, int gameIndex)
            throws DaoException {


        // Setting placement participants according to the game
        ComplexGameDto game = games.get(gameIndex);


        // == If game is upcoming - setting both players for placement
        if (game.getWinner() == null) {

            if (game.getFirstPlayer() != null) {
                placement.addPlayerId(playerDao.get(game.getFirstPlayer().getId()));
            }

            if (game.getSecondPlayer() != null) {
                placement.addPlayerId(playerDao.get(game.getSecondPlayer().getId()));
            }

        } else {


            // == If game is finished, setting looser for placement
            // Winner will be process further
            placement.addPlayerId(playerDao.get(game.getLooser().getId()));
            placement.setFinished(true);
        }

    }


}
