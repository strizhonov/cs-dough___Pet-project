package by.training.core;

import by.training.command.*;
import by.training.connection.ConnectionPool;
import by.training.connection.ConnectionProvider;
import by.training.connection.TransactionManager;
import by.training.connection.TransactionManagerImpl;
import by.training.game.*;
import by.training.organizer.*;
import by.training.player.*;
import by.training.resourse.AppSetting;
import by.training.security.SecurityDirector;
import by.training.security.SecurityProvider;
import by.training.security.SecurityProviderImpl;
import by.training.security.directorimpl.*;
import by.training.tournament.*;
import by.training.user.*;
import by.training.user.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ApplicationContext {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationContext.class);
    private final AtomicBoolean initialized = new AtomicBoolean();
    private final Map<Class<?>, Object> registered = new HashMap<>();


    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        return InstanceHolder.INSTANCE;
    }


    public Object get(Class<?> clazz) {
        return registered.get(clazz);
    }


    void init() {
        if (initialized.get()) {
            LOGGER.warn("Application context has already been initialized.");
            return;
        }

        createAll();
        initialized.set(true);
    }


    void destroy() {
        Iterator<Map.Entry<Class<?>, Object>> iterator = registered.entrySet().iterator();
        while (iterator.hasNext()) {
            Object current = iterator.next().getValue();
            if (current instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) current).close();
                } catch (Exception e) {
                    LOGGER.error("Unable to close connection.", e);
                }
            }
            iterator.remove();
        }
    }


    private void register(Object... objects) throws ApplicationContextException {
        for (Object obj : objects) {
            if (!isBean(obj) || registered.containsKey(obj.getClass())) {
                LOGGER.error("Unable to register object " + obj.getClass().getSimpleName() + ".");
                throw new ApplicationContextException("Unable to register object " +
                        obj.getClass().getSimpleName() + ".");
            }
            registered.put(obj.getClass(), obj);
        }
    }


    private boolean isBean(Object obj) {
        return obj.getClass().getAnnotation(Bean.class) != null;
    }


    private void createAll() {
        AppSetting setting = new AppSetting();
        register(setting);


        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            String sPoolSize = setting.get(AppSetting.SettingName.CONNECTION_POOL_SIZE);
            connectionPool.init(Integer.parseInt(sPoolSize));
        } catch (SQLException e) {
            LOGGER.error("ConnectionPool initialization failed.", e);
            throw new ApplicationContextException("ConnectionPool initialization failed.", e);
        }
        register(connectionPool);


        ConnectionProvider connectionProvider = connectionPool.getConnectionProvider();
        register(connectionProvider);


        TransactionManagerImpl transactionManager = new TransactionManagerImpl(connectionProvider);
        register(transactionManager);


        createDaos(transactionManager);
        createServices(transactionManager);


        BufferWallet appWallet = BufferWallet.getInstance();
        String sWalletId = setting.get(AppSetting.SettingName.APP_WALLET_ID);
        appWallet.init(Long.parseLong(sWalletId));
        register(appWallet);


        ActionCommandProvider commandProvider = new ActionCommandProviderImpl();
        createCommands(commandProvider);
        register(commandProvider);


        SecurityProvider securityProvider = new SecurityProviderImpl();
        createSecurity(securityProvider);
        register(securityProvider);

    }


    private void createDaos(ConnectionProvider connectionProvider) {
        UserDao userDao = new UserDaoImpl(connectionProvider);
        TournamentDao tournamentDao = new TournamentDaoImpl(connectionProvider);
        WalletDao walletDao = new WalletDaoImpl(connectionProvider);
        PlayerDao playerDao = new PlayerDaoImpl(connectionProvider);
        OrganizerDao organizerDao = new OrganizerDaoImpl(connectionProvider);
        GameDao gameDao = new GameDaoImpl(connectionProvider);
        GameServerDao gameServerDao = new GameServerDaoImpl(connectionProvider);

        register(userDao, tournamentDao, walletDao, playerDao, organizerDao, gameDao, gameServerDao);
    }


    private void createServices(TransactionManager transactionManager) {
        UserService userService;
        try {
            userService = new UserServiceImpl(
                    (UserDao) registered.get(UserDaoImpl.class),
                    (WalletDao) registered.get(WalletDaoImpl.class),
                    transactionManager);
        } catch (ServiceException e) {
            LOGGER.error("User Service initialization failed.", e);
            throw new ApplicationContextException("User Service initialization failed.", e);
        }

        GameService gameService = new GameServiceImpl(
                (GameDao) registered.get(GameDaoImpl.class),
                (PlayerDao) registered.get(PlayerDaoImpl.class),
                (GameServerDao) registered.get(GameServerDao.class),
                (TournamentDao) registered.get(TournamentDaoImpl.class),
                (WalletDao) registered.get(WalletDaoImpl.class),
                transactionManager);

        TournamentService tournamentService = new TournamentServiceImpl(
                (TournamentDao) registered.get(TournamentDaoImpl.class),
                (GameDao) registered.get(GameDaoImpl.class),
                (PlayerDao) registered.get(PlayerDaoImpl.class),
                (WalletDao) registered.get(WalletDaoImpl.class),
                (GameServerDao) registered.get(GameServerDaoImpl.class),
                transactionManager);

        PlayerService playerService = new PlayerServiceImpl(
                (PlayerDao) registered.get(PlayerDaoImpl.class),
                transactionManager);

        OrganizerService organizerService = new OrganizerServiceImpl(
                (OrganizerDao) registered.get(OrganizerDaoImpl.class),
                transactionManager);

        register(userService, gameService, tournamentService, playerService, organizerService);
    }


    private void createCommands(ActionCommandProvider commandProvider) {
        PlayerService playerService = (PlayerService) registered.get(PlayerServiceImpl.class);
        OrganizerService organizerService = (OrganizerService) registered.get(OrganizerServiceImpl.class);
        UserService userService = (UserService) registered.get(UserServiceImpl.class);
        GameService gameService = (GameService) registered.get(GameServiceImpl.class);
        TournamentService tournamentService = (TournamentService) registered.get(TournamentServiceImpl.class);

        ActionCommand listTournaments = new ListTournamentsCommand(tournamentService);
        ActionCommand register = new SignUpCommand(userService);
        ActionCommand logIn = new LogInCommand(userService);
        ActionCommand showPlayer = new ShowPlayerCommand(playerService, tournamentService);
        ActionCommand createPlayer = new CreatePlayerCommand(playerService);
        ActionCommand createTournament = new CreateTournamentCommand(tournamentService);
        ActionCommand createOrganizer = new CreateOrganizerCommand(organizerService);
        ActionCommand toTournamentPage = new ToTournamentPageCommand(tournamentService, organizerService);
        ActionCommand changeLanguageToEn = new ChangeLanguageToEnCommand();
        ActionCommand changeLanguageToRu = new ChangeLanguageToRuCommand();
        ActionCommand toUserPage = new ToUserPageCommand(userService);
        ActionCommand toHomePage = new ToHomePageCommand(gameService);
        ActionCommand saveUserPhoto = new UploadUserPhotoCommand(userService);
        ActionCommand getUserPhoto = new GetUserPhotoCommand(userService);
        ActionCommand toOneGameBracket = new ToBracketPageCommand(gameService);
        ActionCommand toGamePage = new ToGamePageCommand(gameService);
        ActionCommand updateUsername = new UpdateUsernameCommand(userService);
        ActionCommand logOut = new LogOutCommand();
        ActionCommand updateEmail = new UpdateEmailCommand(userService);
        ActionCommand changeUserLanguageToRu = new ChangeUserLanguageToRuCommand(userService);
        ActionCommand changeUserLanguageToEn = new ChangeUserLanguageToEnCommand(userService);
        ActionCommand showOrganizer = new ShowOrganizerCommand(organizerService);
        ActionCommand listGames = new ListGamesCommand(gameService);
        ActionCommand listPlayers = new ListPlayersCommand(playerService);
        ActionCommand getPlayerPhoto = new GetPlayerPhotoCommand(playerService);
        ActionCommand getTournamentLogo = new GetTournamentLogoCommand(tournamentService);
        ActionCommand getOrganizerLogo = new GetOrganizerLogoCommand(organizerService);
        ActionCommand joinTournament = new JoinTournamentCommand(tournamentService);
        ActionCommand leaveTournament = new LeaveTournamentCommand(tournamentService);
        ActionCommand showParticipants = new ShowParticipantsCommand(gameService, tournamentService);
        ActionCommand increaseFirstPlayerCount = new IncreaseFirstPlayerCountCommand(gameService);
        ActionCommand increaseSecondPlayerCount = new IncreaseSecondPlayerCountCommand(gameService);
        ActionCommand deposit = new DepositCommand(userService);
        ActionCommand withdraw = new WithdrawCommand(userService);
        ActionCommand toOrganizerEditing = new ToOrganizerEditingCommand(organizerService, tournamentService);
        ActionCommand toOrganizerTournaments = new ToOrganizerTournamentsCommand(tournamentService);
        ActionCommand updateOrganizer = new UpdateOrganizerCommand(organizerService);
        ActionCommand deleteOrganizer = new DeleteOrganizerCommand(organizerService);
        ActionCommand listPlayerGames = new ListPlayerGamesCommand(gameService, playerService);
        ActionCommand listPlayerTournaments = new ListPlayerTournamentsCommand(tournamentService, playerService);
        ActionCommand toPlayerEditing = new ToPlayerEditingCommand(playerService, tournamentService);
        ActionCommand deleteTournament = new DeleteTournamentCommand(tournamentService);
        ActionCommand updatePlayer = new UpdatePlayerCommand(playerService);

        commandProvider.register(listTournaments, register, logIn, showPlayer, createPlayer,
                createTournament, createOrganizer, toTournamentPage, changeLanguageToEn, changeLanguageToRu, toUserPage,
                toHomePage, saveUserPhoto, getUserPhoto, toOneGameBracket, toGamePage, logOut, updateUsername,
                updateEmail, changeUserLanguageToEn, changeUserLanguageToRu, showOrganizer, listGames, listPlayers,
                getPlayerPhoto, getTournamentLogo, getOrganizerLogo, joinTournament, showParticipants,
                increaseFirstPlayerCount, increaseSecondPlayerCount, withdraw, deposit, toOrganizerEditing,
                toOrganizerTournaments, updateOrganizer, deleteOrganizer, listPlayerGames, listPlayerTournaments,
                toPlayerEditing, deleteTournament, updatePlayer, leaveTournament);
    }


    private void createSecurity(SecurityProvider securityProvider) {
        SecurityDirector forAdmin = new ForAdminAccessDirector();
        SecurityDirector forAnon = new ForAnonymousAccessDirector();
        SecurityDirector forAny = new ForAnyAccessDirector();
        SecurityDirector forOrg = new ForAnyOrganizerAccessDirector();
        SecurityDirector forPlayer = new ForAnyPlayerAccessDirector();
        SecurityDirector forNonOrg = new ForNonOrganizerAccessDirector();
        SecurityDirector forNonPlayer = new ForNonPlayerUserAccessDirector();
        SecurityDirector forOrgOwner = new ForTournamentOwnerAccessDirector(
                (OrganizerService) registered.get(OrganizerService.class));
        SecurityDirector forUser = new ForUserAccessDirector();


        securityProvider.register(forAdmin, forAnon, forAny, forOrg, forPlayer, forNonOrg,
                forNonPlayer, forOrgOwner, forUser);
    }


    private static class InstanceHolder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
    }

}
