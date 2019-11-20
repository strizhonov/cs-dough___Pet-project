package by.training.connection;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    private static final String DB_PROPERTIES_FILE = "database";
    private static final String DB_DRIVER_KEY = "driver";
    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";
    private static final String URL_KEY = "url";
    private static final String CONNECTION_LIFETIME_MS_KEY = "connection_lifetime_ms";
    private static final String EXECUTOR_DELAY_KEY = "terminator_executor_delay";
    private static final String EXECUTOR_PERIOD_KEY = "terminator_executor_period";
    private static final String EXECUTOR_TIME_UNIT_KEY = "terminator_executor_period_time_unit";
    private static final String CONNECTION_WAITING_TIMEOUT_MS_KEY = "max_connection_waiting_time_out";

    private final List<Connection> available = new ArrayList<>();
    private final Map<Connection, Date> borrowed = new HashMap<>();
    private final Lock lock = new ReentrantLock();
    private final ConnectionProvider connectionProvider = new ConnectionProxyProvider(this);
    private final ScheduledExecutorService terminatorExecutorService = Executors.newSingleThreadScheduledExecutor();
    private int poolSize;
    private Properties properties;

    private ConnectionPool() {

    }

    private static class InstanceHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void init(int poolSize) throws SQLException {
        this.poolSize = poolSize;
        initProperties();
        register();
        initConnections(poolSize);
        initConnectionReview();
    }

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
                    String sWaitingTimeout = properties.getProperty(CONNECTION_WAITING_TIMEOUT_MS_KEY);
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
            available.add(connection);
            borrowed.remove(connection);
        } finally {
            lock.unlock();
        }
    }

    private void initProperties() throws SQLException {
        properties = new Properties();
        try {
            Reader reader = new InputStreamReader(new FileInputStream(DB_PROPERTIES_FILE));
            properties.load(reader);
        } catch (IOException e) {
            LOGGER.error("Unable to load database properties.", e);
            throw new SQLException("Unable to load database properties.", e);
        }

    }

    private void initConnectionReview() {
        String sConnectionLifetime = properties.getProperty(CONNECTION_LIFETIME_MS_KEY);
        long connectionLifetime = Long.parseLong(sConnectionLifetime);
        Runnable runnable = new ConnectionTerminator(borrowed, connectionLifetime);

        String sExecutorDelay = properties.getProperty(EXECUTOR_DELAY_KEY);
        long executorDelay = Long.parseLong(sExecutorDelay);
        String sExecutorPeriod = properties.getProperty(EXECUTOR_PERIOD_KEY);
        long executorPeriod = Long.parseLong(sExecutorPeriod);
        String sExecutorTimeUnit = properties.getProperty(EXECUTOR_TIME_UNIT_KEY);
        TimeUnit executorTimeUnit = TimeUnit.valueOf(sExecutorTimeUnit);

        terminatorExecutorService.scheduleAtFixedRate(runnable, executorDelay, executorPeriod, executorTimeUnit);
    }

    private void register() throws SQLException {
        String dbDriver = properties.getProperty(DB_DRIVER_KEY);
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
        String url = properties.getProperty(URL_KEY);
        String user = properties.getProperty(USER_KEY);
        String password = properties.getProperty(PASSWORD_KEY);

        return DriverManager.getConnection(url, user, password);
    }

}
