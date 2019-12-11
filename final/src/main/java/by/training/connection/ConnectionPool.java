package by.training.connection;

import by.training.core.AppSetting;
import by.training.core.ApplicationContext;
import by.training.core.Bean;
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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Bean
public final class ConnectionPool implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private final AtomicBoolean initialized = new AtomicBoolean();
    private final List<Connection> available = new ArrayList<>();
    private final Map<Connection, Date> borrowed = new HashMap<>();
    private final Lock lock = new ReentrantLock();
    private final Condition empty = lock.newCondition();
    private final ConnectionProvider connectionProvider = new ConnectionProxyProvider(this);
    private final ScheduledExecutorService terminatorExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private int poolSize;

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
                    empty.await();
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
                empty.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    private void initConnectionTerminator() {
        String sConnectionLifetime = setting.get(AppSetting.SettingName.CONNECTION_POOL_SIZE);
        long connectionLifetime = Long.parseLong(sConnectionLifetime);
        Runnable runnable = new ConnectionTerminator(borrowed, connectionLifetime);

        String sExecutorDelay = setting.get(AppSetting.SettingName.TERMINATOR_EXECUTOR_DELAY);
        long executorDelay = Long.parseLong(sExecutorDelay);
        String sExecutorPeriod = setting.get(AppSetting.SettingName.TERMINATOR_EXECUTOR_PERIOD);
        long executorPeriod = Long.parseLong(sExecutorPeriod);
        String sExecutorTimeUnit = setting.get(AppSetting.SettingName.TERMINATOR_EXECUTOR_TIME_UNIT);
        TimeUnit executorTimeUnit = TimeUnit.valueOf(sExecutorTimeUnit);

        terminatorExecutorService.scheduleAtFixedRate(runnable, executorDelay, executorPeriod, executorTimeUnit);
    }

    private void register() throws SQLException {
        String dbDriver = setting.get(AppSetting.SettingName.DB_DRIVER);
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
        String url = setting.get(AppSetting.SettingName.DB_URL);
        String user = setting.get(AppSetting.SettingName.DB_USER);
        String password = setting.get(AppSetting.SettingName.DB_PASSWORD);

        return DriverManager.getConnection(url, user, password);
    }

}
