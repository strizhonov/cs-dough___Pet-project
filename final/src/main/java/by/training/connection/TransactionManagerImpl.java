package by.training.connection;

import by.training.core.Bean;
import by.training.resourse.AttributesContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Bean
public class TransactionManagerImpl implements TransactionManager, ConnectionProvider {

    private static final Logger LOGGER = LogManager.getLogger(TransactionManagerImpl.class);
    private final ThreadLocal<Connection> localConnection = new ThreadLocal<>();
    private final Lock lock = new ReentrantLock();
    private final ConnectionProvider connectionProvider;


    public TransactionManagerImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }


    @Override
    public void startTransaction() throws TransactionException {
        lock.lock();
        try {
            if (localConnection.get() == null) {
                Connection connection = connectionProvider.getConnection();
                connection.setAutoCommit(false);
                localConnection.set(connection);
            }
        } catch (SQLException e) {
            LOGGER.warn("Unable to perform start of the transaction.", e);
            throw new TransactionException("Unable to perform start of the transaction.", e);
        }
    }


    @Override
    public void commitTransaction() throws TransactionException {
        try {
            Connection connection = localConnection.get();
            if (connection != null) {
                connection.commit();
                connection.setAutoCommit(true);
                connection.close();
                localConnection.remove();
            }
        } catch (SQLException e) {
            LOGGER.warn("Unable to perform transaction committing.", e);
            throw new TransactionException("Unable to perform transaction committing.", e);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void rollbackTransaction() {
        try {
            Connection connection = localConnection.get();
            if (connection != null) {
                connection.rollback();
                connection.setAutoCommit(true);
                connection.close();
                localConnection.remove();
            }
        } catch (SQLException e) {
            LOGGER.warn("Unable to perform transaction rollback.", e);
            throw new TransactionRollbackException("Unable to perform transaction rollback.", e);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        return localConnection.get() != null
                ? proxy()
                : connectionProvider.getConnection();
    }


    private Connection proxy() {
        Connection connection = localConnection.get();
        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (AttributesContainer.CLOSE.toString().equals(method.getName())) {
                        LOGGER.info("Method close of proxy called.");
                        return null;
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }


}
