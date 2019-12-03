package by.training.appentry;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandProvider;
import by.training.command.ActionCommandProviderImpl;
import by.training.command.impl.*;
import by.training.connection.ConnectionPool;
import by.training.connection.ConnectionProvider;
import by.training.connection.TransactionManagerImpl;
import by.training.dao.*;
import by.training.security.AccessAllowedForType;
import by.training.security.SecurityDirector;
import by.training.security.SecurityProvider;
import by.training.security.SecurityProviderImpl;
import by.training.security.supervisorimpl.*;
import by.training.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class ApplicationLoader {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationLoader.class);

    private final AtomicBoolean initialized = new AtomicBoolean();
    private Map<Class<?>, Object> registered = new HashMap<>();

    private ApplicationLoader() {

    }

    private static class InstanceHolder {
        private static final ApplicationLoader INSTANCE = new ApplicationLoader();
    }

    public static ApplicationLoader getInstance() {
        return ApplicationLoader.InstanceHolder.INSTANCE;
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

    private void register(Object... objects) throws ApplicationLoaderException {
        for (Object obj : objects) {
            if (!isBean(obj) || registered.containsKey(obj.getClass())) {
                LOGGER.error("Unable to register object " + obj.getClass().getSimpleName() + ".");
                throw new ApplicationLoaderException("Unable to register object " +
                        obj.getClass().getSimpleName() + ".");
            }
            registered.put(obj.getClass(), obj);
        }
    }

    private boolean isBean(Object obj) {
        return obj.getClass().getAnnotation(Bean.class) != null;
    }

    private void createAll() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.init(10);
        } catch (SQLException e) {
            LOGGER.error("ConnectionPool initialization failed.", e);
            throw new ApplicationLoaderException("ConnectionPool initialization failed.", e);
        }
        ConnectionProvider connectionProvider = connectionPool.getConnectionProvider();

        TransactionManagerImpl userTransactionManager = new TransactionManagerImpl(connectionProvider);
        TransactionManagerImpl tournamentTransactionManager = new TransactionManagerImpl(connectionProvider);
        TransactionManagerImpl walletTransactionManager = new TransactionManagerImpl(connectionProvider);
        TransactionManagerImpl playerTransactionManager = new TransactionManagerImpl(connectionProvider);
        TransactionManagerImpl organizerTransactionManager = new TransactionManagerImpl(connectionProvider);
        TransactionManagerImpl gameTransactionManager = new TransactionManagerImpl(connectionProvider);

        UserDao userDao = new UserDaoImpl(userTransactionManager);
        TournamentDao tournamentDao = new TournamentDaoImpl(tournamentTransactionManager);
        WalletDao walletDao = new WalletDaoImpl(walletTransactionManager);
        PlayerDao playerDao = new PlayerDaoImpl(playerTransactionManager);
        OrganizerDao organizerDao = new OrganizerDaoImpl(organizerTransactionManager);
        GameDao gameDao = new GameDaoImpl(gameTransactionManager);

        WalletService walletService = new WalletServiceImpl(walletTransactionManager, walletDao);
        UserService userService;
        try {
            userService = new UserServiceImpl(userDao, walletService, userTransactionManager);
        } catch (ServiceException e) {
            LOGGER.error("User Service initialization failed.", e);
            throw new ApplicationLoaderException("User Service initialization failed.", e);
        }
        PlayerService playerService = new PlayerServiceImpl(playerDao, userService, playerTransactionManager);
        OrganizerService organizerService = new OrganizerServiceImpl(organizerDao, userService, organizerTransactionManager);
        GameService gameService = new GameServiceImpl(gameDao, gameTransactionManager);
        TournamentService tournamentService = new TournamentServiceImpl(tournamentDao, gameService, tournamentTransactionManager);
        ActionCommandProvider commandProvider = new ActionCommandProviderImpl();

        ActionCommand listTournamentsCommand = new ListTournamentsCommand(tournamentService);
        ActionCommand toLoginPageCommand = new ToLoginPageCommand();
        ActionCommand registerCommand = new RegisterCommand(userService);
        ActionCommand logInCommand = new LogInCommand(userService);
        ActionCommand showPlayerCommand = new ShowPlayerCommand(playerService);
        ActionCommand createPlayerCommand = new CreatePlayerCommand(playerService);
        ActionCommand toPlayerCreationCommand = new ToPlayerCreationCommand();
        ActionCommand toTournamentCreationCommand = new ToTournamentCreationCommand();
        ActionCommand createTournamentCommand = new CreateTournamentCommand(tournamentService);
        ActionCommand createOrganizerCommand = new CreateOrganizerCommand(organizerService);
        ActionCommand toOrganizerCreationCommand = new ToOrganizerCreationCommand();
        ActionCommand toTournamentPageCommand = new ToTournamentPageCommand(tournamentService);
        ActionCommand changeLanguageToEnCommand = new ChangeLanguageToEnCommand();
        ActionCommand changeLanguageToRuCommand = new ChangeLanguageToRuCommand();
        ActionCommand toUserPageCommand = new ToUserPageCommand(userService);
        ActionCommand toHomePageCommand = new ToHomePageCommand(gameService);

        commandProvider.register(listTournamentsCommand, toLoginPageCommand, registerCommand, logInCommand,
                showPlayerCommand, createPlayerCommand, toPlayerCreationCommand, toTournamentCreationCommand,
                createTournamentCommand, createOrganizerCommand, toOrganizerCreationCommand, toTournamentPageCommand,
                changeLanguageToEnCommand, changeLanguageToRuCommand, toUserPageCommand, toHomePageCommand);

        SecurityProvider securityProvider = new SecurityProviderImpl();
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
        SecurityDirector forUserOwner = new ForUserOwnerAccessDirector();

        securityProvider.register(forAdmin, forAnon, forAny, forOrg, forPlayer, forNonOrg, forNonPlayer, forOrgOwner,
                forPlayerOwner, forUser, forUserOwner);

        register(connectionProvider, userDao, tournamentDao, walletDao, playerDao, organizerDao,
                gameDao, userService, tournamentService, playerService, organizerService, gameService,
                commandProvider, securityProvider);

    }

}
