package by.training.appentry;

import by.training.command.ActionCommandProviderImpl;
import by.training.command.impl.CreateOrganizerCommand;
import by.training.command.impl.CreatePlayerCommand;
import by.training.command.impl.CreateTournamentCommand;
import by.training.command.impl.ListTournamentsCommand;
import by.training.command.impl.LogInCommand;
import by.training.command.impl.RegisterCommand;
import by.training.command.impl.ShowPlayerCommand;
import by.training.command.impl.ToLoginPageCommand;
import by.training.command.impl.ToOrganizerCreationCommand;
import by.training.command.impl.ToPlayerCreationCommand;
import by.training.command.impl.ToTournamentCreationCommand;
import by.training.command.impl.ToTournamentPageCommand;
import by.training.connection.ConnectionPool;
import by.training.connection.ConnectionProvider;
import by.training.dao.GameDaoImpl;
import by.training.dao.OrganizerDaoImpl;
import by.training.dao.PlayerDaoImpl;
import by.training.dao.TournamentDaoImpl;
import by.training.dao.UserDaoImpl;
import by.training.dao.WalletDaoImpl;
import by.training.service.GameServiceImpl;
import by.training.service.OrganizerService;
import by.training.service.OrganizerServiceImpl;
import by.training.service.PlayerServiceImpl;
import by.training.service.TournamentServiceImpl;
import by.training.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleBeanApplicationContext {

    private static final Logger LOGGER = LogManager.getLogger(SingleBeanApplicationContext.class);

    private final AtomicBoolean initialized = new AtomicBoolean();
    private Map<Class, Object> registered = new HashMap<>();

    private SingleBeanApplicationContext() {

    }

    private static class InstanceHolder {
        private static final SingleBeanApplicationContext INSTANCE = new SingleBeanApplicationContext();
    }

    public static SingleBeanApplicationContext getInstance() {
        return SingleBeanApplicationContext.InstanceHolder.INSTANCE;
    }

    public Object get(Class clazz) {
        return registered.get(clazz);
    }

    boolean init() {
        if (initialized.get()) {
            LOGGER.warn("Application context has already been initialized.");
            return false;
        }

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.init(10);
        } catch (SQLException e) {
            LOGGER.error("ConnectionPool initialization failed.", e);
            throw new IllegalStateException("ConnectionPool initialization failed.", e);
        }
        ConnectionProvider connectionProvider = connectionPool.getConnectionProvider();

        UserDaoImpl userDao = new UserDaoImpl(connectionProvider);
        TournamentDaoImpl tournamentDao = new TournamentDaoImpl(connectionProvider);
        WalletDaoImpl walletDao = new WalletDaoImpl(connectionProvider);
        PlayerDaoImpl playerDao = new PlayerDaoImpl(connectionProvider);
        OrganizerDaoImpl organizerDao = new OrganizerDaoImpl(connectionProvider);
        GameDaoImpl gameDao = new GameDaoImpl(connectionProvider);

        UserServiceImpl userService = new UserServiceImpl(userDao, walletDao);
        TournamentServiceImpl tournamentService = new TournamentServiceImpl(tournamentDao);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerDao);
        OrganizerService organizerService = new OrganizerServiceImpl(organizerDao);
        GameServiceImpl gameService = new GameServiceImpl(gameDao);

        ActionCommandProviderImpl commandProvider = new ActionCommandProviderImpl();

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

        commandProvider.register(listTournamentsCommand, toLoginPageCommand, registerCommand, logInCommand,
                showPlayerCommand, createPlayerCommand, toPlayerCreationCommand, toTournamentCreationCommand,
                createTournamentCommand, createOrganizerCommand, toOrganizerCreationCommand, toTournamentPageCommand);

        try {
            register(connectionProvider, userDao, tournamentDao, walletDao, playerDao, organizerDao,
                    gameDao, userService, tournamentService, playerService, organizerService, gameService,
                    commandProvider);
        } catch (SingleBeanApplicationContextException e) {
            LOGGER.error("Beans' registering failed.", e);
            throw new IllegalStateException("Beans' registering failed.", e);
        }

        initialized.set(true);
        return true;
    }

    void destroy() {
        Iterator<Map.Entry<Class, Object>> iterator = registered.entrySet().iterator();
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

    private void register(Object... objects) throws SingleBeanApplicationContextException {
        for (Object obj : objects) {
            if (registered.containsKey(obj.getClass())) {
                LOGGER.error("Unable to register object " + obj.getClass().getSimpleName() +
                        ", it's already registered.");
                throw new SingleBeanApplicationContextException("Unable to register object " +
                        obj.getClass().getSimpleName() + ", it's already registered.");
            }
            registered.put(obj.getClass(), obj);
        }
    }

}
