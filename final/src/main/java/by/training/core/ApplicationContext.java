package by.training.core;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandProvider;
import by.training.command.ActionCommandProviderImpl;
import by.training.common.ChangeLanguageToEnCommand;
import by.training.common.ChangeLanguageToRuCommand;
import by.training.common.ServiceException;
import by.training.common.ToHomePageCommand;
import by.training.connection.ConnectionPool;
import by.training.connection.ConnectionProvider;
import by.training.connection.TransactionManager;
import by.training.connection.TransactionManagerImpl;
import by.training.game.*;
import by.training.security.SecurityDirector;
import by.training.security.SecurityProvider;
import by.training.security.SecurityProviderImpl;
import by.training.security.supervisorimpl.*;
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

    private static class InstanceHolder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
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

        register(userDao, tournamentDao, walletDao, playerDao, organizerDao, gameDao);
    }

    private void createServices(TransactionManager transactionManager) {
        UserService userService;
        try {
            userService = new UserServiceImpl(
                    (UserDao) registered.get(UserDaoImpl.class),
                    (WalletDao) registered.get(WalletDaoImpl.class),
                    (PlayerDao) registered.get(PlayerDaoImpl.class),
                    (OrganizerDao) registered.get(OrganizerDaoImpl.class),
                    transactionManager);
        } catch (ServiceException e) {
            LOGGER.error("User Service initialization failed.", e);
            throw new ApplicationContextException("User Service initialization failed.", e);
        }

        GameService gameService = new GameServiceImpl((GameDao) registered.get(GameDaoImpl.class), transactionManager);
        TournamentService tournamentService = new TournamentServiceImpl(
                (TournamentDao) registered.get(TournamentDaoImpl.class), (GameDao) registered.get(GameDaoImpl.class),
                transactionManager);

        register(userService, gameService, tournamentService);
    }

    private void createCommands(ActionCommandProvider commandProvider) {
        final UserService userService = (UserService) registered.get(UserServiceImpl.class);
        final GameService gameService = (GameService) registered.get(GameServiceImpl.class);
        final TournamentService tournamentService =  (TournamentService) registered.get(TournamentServiceImpl.class);

        ActionCommand listTournamentsCommand = new ListTournamentsCommand(tournamentService);
        ActionCommand toLoginPageCommand = new ToLoginPageCommand();
        ActionCommand registerCommand = new RegisterCommand(userService);
        ActionCommand logInCommand = new LogInCommand(userService);
        ActionCommand showPlayerCommand = new ShowPlayerCommand(userService);
        ActionCommand createPlayerCommand = new CreatePlayerCommand(userService);
        ActionCommand toPlayerCreationCommand = new ToPlayerCreationCommand();
        ActionCommand toTournamentCreationCommand = new ToTournamentCreationCommand();
        ActionCommand createTournamentCommand = new CreateTournamentCommand(tournamentService);
        ActionCommand createOrganizerCommand = new CreateOrganizerCommand(userService);
        ActionCommand toOrganizerCreationCommand = new ToOrganizerCreationCommand();
        ActionCommand toTournamentPageCommand = new ToTournamentPageCommand(tournamentService, userService);
        ActionCommand changeLanguageToEnCommand = new ChangeLanguageToEnCommand();
        ActionCommand changeLanguageToRuCommand = new ChangeLanguageToRuCommand();
        ActionCommand toUserPageCommand = new ToUserPageCommand(userService);
        ActionCommand toHomePageCommand = new ToHomePageCommand(gameService);
        ActionCommand saveUserPhoto = new UploadUserPhotoCommand(userService);
        ActionCommand getUserPhoto = new GetUserPhotoCommand(userService);
        ActionCommand toOneGameBracket = new ToOneGameBracketCommand(tournamentService, gameService);
        ActionCommand toGamePage = new ToGamePageCommand(gameService);
        ActionCommand updateUsername = new UpdateUsernameCommand(userService);
        ActionCommand logOut = new LogOutCommand();
        ActionCommand updateEmail = new UpdateEmailCommand(userService);
        ActionCommand changeUserLanguageToRu = new ChangeUserLanguageToRuCommand(userService);
        ActionCommand changeUserLanguageToEn = new ChangeUserLanguageToEnCommand(userService);
        ActionCommand showOrganizer = new ShowOrganizerCommand(userService);
        ActionCommand listGames = new ListGamesCommand(gameService);
        ActionCommand listPlayers = new ListPlayersCommand(userService);
        ActionCommand getPlayerPhoto = new GetPlayerPhotoCommand(userService);
        ActionCommand getTournamentLogo = new GetTournamentLogoCommand(tournamentService);
        ActionCommand getOrganizerLogo = new GetOrganizerLogoCommand(userService);

        commandProvider.register(listTournamentsCommand, toLoginPageCommand, registerCommand, logInCommand,
                showPlayerCommand, createPlayerCommand, toPlayerCreationCommand, toTournamentCreationCommand,
                createTournamentCommand, createOrganizerCommand, toOrganizerCreationCommand, toTournamentPageCommand,
                changeLanguageToEnCommand, changeLanguageToRuCommand, toUserPageCommand, toHomePageCommand,
                saveUserPhoto, getUserPhoto, toOneGameBracket, toGamePage, logOut, updateUsername, updateEmail,
                changeUserLanguageToEn, changeUserLanguageToRu, showOrganizer, listGames, listPlayers, getPlayerPhoto,
                getTournamentLogo, getOrganizerLogo);
    }

    private void createSecurity(SecurityProvider securityProvider) {
        SecurityDirector forAdmin = new ForAdminAccessDirector();
        SecurityDirector forAnon = new ForAnonymousAccessDirector();
        SecurityDirector forAny = new ForAnyAccessDirector();
        SecurityDirector forOrg = new ForAnyOrganizerAccessDirector();
        SecurityDirector forPlayer = new ForAnyPlayerAccessDirector();
        SecurityDirector forNonOrg = new ForNonOrganizerAccessDirector();
        SecurityDirector forNonPlayer = new ForNonPlayerUserAccessDirector();
        SecurityDirector forOrgOwner = new ForOrganizerOwnerAccessDirector();
        SecurityDirector forPlayerOwner = new ForPlayerOwnerAccessDirector();
        SecurityDirector forUser = new ForUserAccessDirector();

        securityProvider.register(forAdmin, forAnon, forAny, forOrg, forPlayer, forNonOrg, forNonPlayer, forOrgOwner,
                forPlayerOwner, forUser);
    }

}
