package by.training.appentry;

import by.training.command.ActionCommandProvider;
import by.training.command.ActionCommandProviderImpl;
import by.training.command.impl.*;
import by.training.connection.ConnectionPool;
import by.training.connection.ConnectionProvider;
import by.training.connection.TransactionManager;
import by.training.connection.TransactionManagerImpl;
import by.training.dao.*;
import by.training.security.SecurityService;
import by.training.security.SecurityServiceImpl;
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
        TransactionManager transactionManager = new TransactionManagerImpl(connectionProvider);

        UserDao userDao = new UserDaoImpl(transactionManager);
        TournamentDao tournamentDao = new TournamentDaoImpl(transactionManager);
        WalletDao walletDao = new WalletDaoImpl(transactionManager);
        PlayerDao playerDao = new PlayerDaoImpl(transactionManager);
        OrganizerDao organizerDao = new OrganizerDaoImpl(transactionManager);
        GameDao gameDao = new GameDaoImpl(transactionManager);

        WalletService walletService = new WalletServiceImpl(transactionManager, walletDao);
        UserService userService;
        try {
            userService = new UserServiceImpl(userDao, walletService, transactionManager);
        } catch (ServiceException e) {
            LOGGER.error("User Service initialization failed.", e);
            throw new ApplicationLoaderException("User Service initialization failed.", e);
        }
        PlayerService playerService = new PlayerServiceImpl(playerDao, userService, transactionManager);
        OrganizerService organizerService = new OrganizerServiceImpl(organizerDao, userService, transactionManager);
        GameService gameService = new GameServiceImpl(gameDao, transactionManager);
        TournamentService tournamentService = new TournamentServiceImpl(tournamentDao, gameService, transactionManager);
        ActionCommandProvider commandProvider = new ActionCommandProviderImpl();

        ListTournamentsCommand listTournamentsCommand = new ListTournamentsCommand(tournamentService);
        ToLoginPageCommand toLoginPageCommand = new ToLoginPageCommand();
        RegisterCommand registerCommand = new RegisterCommand(userService);
        LogInCommand logInCommand = new LogInCommand(userService);
        ShowPlayerCommand showPlayerCommand = new ShowPlayerCommand(playerService);
        CreatePlayerCommand createPlayerCommand = new CreatePlayerCommand(playerService);
        ToPlayerCreationCommand toPlayerCreationCommand = new ToPlayerCreationCommand();
        ToTournamentCreationCommand toTournamentCreationCommand = new ToTournamentCreationCommand();
        CreateTournamentCommand createTournamentCommand = new CreateTournamentCommand(tournamentService);
        CreateOrganizerCommand createOrganizerCommand = new CreateOrganizerCommand(organizerService);
        ToOrganizerCreationCommand toOrganizerCreationCommand = new ToOrganizerCreationCommand();
        ToTournamentPageCommand toTournamentPageCommand = new ToTournamentPageCommand(tournamentService);
        ChangeLanguageToEnCommand changeLanguageToEnCommand = new ChangeLanguageToEnCommand();
        ChangeLanguageToRuCommand changeLanguageToRuCommand = new ChangeLanguageToRuCommand();
        ToUserPageCommand toUserPageCommand = new ToUserPageCommand(userService);

        commandProvider.register(listTournamentsCommand, toLoginPageCommand, registerCommand, logInCommand,
                showPlayerCommand, createPlayerCommand, toPlayerCreationCommand, toTournamentCreationCommand,
                createTournamentCommand, createOrganizerCommand, toOrganizerCreationCommand, toTournamentPageCommand,
                changeLanguageToEnCommand, changeLanguageToRuCommand, toUserPageCommand);

        SecurityService securityService = new SecurityServiceImpl();

        register(connectionProvider, userDao, tournamentDao, walletDao, playerDao, organizerDao,
                gameDao, userService, tournamentService, playerService, organizerService, gameService,
                commandProvider, securityService);

    }

}
