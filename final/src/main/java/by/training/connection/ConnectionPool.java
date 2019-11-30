package by.training.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final String DB_PROPERTIES_FILE = "database";
    private static final String DB_DRIVER_KEY = "driver";
    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";
    private static final String URL_KEY = "url";
    private static final String CONNECTION_LIFETIME_MS_KEY = "connection.lifetime.ms";
    private static final String EXECUTOR_DELAY_KEY = "terminator.executor.delay";
    private static final String EXECUTOR_PERIOD_KEY = "terminator.executor.period";
    private static final String EXECUTOR_TIME_UNIT_KEY = "terminator.executor.period.time.unit";
    private static final String CONNECTION_WAITING_TIMEOUT_MS_KEY = "max.connection.waiting.time.out.ms";

    private final AtomicBoolean initialized = new AtomicBoolean();
    private final List<Connection> available = new ArrayList<>();
    private final Map<Connection, Date> borrowed = new HashMap<>();
    private final Lock lock = new ReentrantLock();
    private final ConnectionProvider connectionProvider = new ConnectionProxyProvider(this);
    private final ScheduledExecutorService terminatorExecutorService = Executors.newSingleThreadScheduledExecutor();
    private int poolSize;
    private ResourceBundle resourceBundle;

    private ConnectionPool() {

    }

    private static class InstanceHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void init(int poolSize) throws SQLException {
        if (!initialized.get()) {
            this.poolSize = poolSize;
            initProperties();

            register();
            initConnections(poolSize);
            initConnectionTerminator();

            initialized.set(true);
        }
    }

    @Override
    public void close() throws SQLException {
        lock.lock();
        try {
            terminatorExecutorService.shutdownNow();

            for (Connection connection : available) {
                connection.close();
            }
            for (Connection connection : borrowed.keySet()) {
                connection.close();
            }

            deregister();
        } finally {
            lock.unlock();
        }

    }

    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    Connection getConnection() throws SQLException {
        lock.lock();
        Connection connection;
        try {
            while (available.isEmpty()) {
                if (borrowed.size() == poolSize) {
                    String sWaitingTimeout = resourceBundle.getString(CONNECTION_WAITING_TIMEOUT_MS_KEY);
                    long waitingTimeout = Long.parseLong(sWaitingTimeout);
                    wait(waitingTimeout);
                }
                if (borrowed.size() < poolSize) {
                    connection = getConnectionFromDriver();
                    available.add(connection);
                }
            }
            connection = available.remove(0);
            borrowed.put(connection, new Date());
            return connection;
        } catch (InterruptedException e) {
            LOGGER.error("Unable to get database connection.", e);
            throw new SQLException("Unable to get database connection.", e);
        } finally {
            lock.unlock();
        }

    }

    void release(Connection connection) {
        lock.lock();
        try {
            if (borrowed.remove(connection) != null) {
                available.add(connection);
            }
        } finally {
            lock.unlock();
        }
    }

    private void initProperties() {
        resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);
    }

    private void initConnectionTerminator() {
        String sConnectionLifetime = resourceBundle.getString(CONNECTION_LIFETIME_MS_KEY);
        long connectionLifetime = Long.parseLong(sConnectionLifetime);
        Runnable runnable = new ConnectionTerminator(borrowed, connectionLifetime);

        String sExecutorDelay = resourceBundle.getString(EXECUTOR_DELAY_KEY);
        long executorDelay = Long.parseLong(sExecutorDelay);
        String sExecutorPeriod = resourceBundle.getString(EXECUTOR_PERIOD_KEY);
        long executorPeriod = Long.parseLong(sExecutorPeriod);
        String sExecutorTimeUnit = resourceBundle.getString(EXECUTOR_TIME_UNIT_KEY);
        TimeUnit executorTimeUnit = TimeUnit.valueOf(sExecutorTimeUnit);

        terminatorExecutorService.scheduleAtFixedRate(runnable, executorDelay, executorPeriod, executorTimeUnit);
    }

    private void register() throws SQLException {
        String dbDriver = resourceBundle.getString(DB_DRIVER_KEY);
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Unable to register database driver.", e);
            throw new SQLException("Unable to register database driver.", e);
        }
    }

    private void deregister() throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            DriverManager.deregisterDriver(driver);
        }
    }

    private void initConnections(int poolSize) throws SQLException {
        for (int i = 0; i < poolSize; i++) {
            Connection connection = getConnectionFromDriver();
            available.add(connection);
        }
    }

    private Connection getConnectionFromDriver() throws SQLException {
        String url = resourceBundle.getString(URL_KEY);
        String user = resourceBundle.getString(USER_KEY);
        String password = resourceBundle.getString(PASSWORD_KEY);

        return DriverManager.getConnection(url, user, password);
    }

}
