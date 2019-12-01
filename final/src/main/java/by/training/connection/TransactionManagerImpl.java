package by.training.connection;

import by.training.appentry.Bean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Bean
public class TransactionManagerImpl implements TransactionManager, ConnectionProvider {

    private static final Logger LOGGER = LogManager.getLogger(TransactionManagerImpl.class);
    private ThreadLocal<Connection> localConnection = new ThreadLocal<>();
    private ConnectionProvider connectionProvider;

    public TransactionManagerImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public boolean startTransaction() throws TransactionCommonException {
        try {
            if (localConnection.get() == null) {
                Connection connection = connectionProvider.getConnection();
                connection.setAutoCommit(false);
                localConnection.set(connection);
                return true;
            } else {
                LOGGER.warn("Transaction has already started.");
                return false;
            }
        } catch (SQLException e) {
            LOGGER.warn("Unable to perform start of the transaction.", e);
            throw new TransactionCommonException("Unable to perform start of the transaction.", e);
        }
    }

    @Override
    public boolean commitTransaction() throws TransactionCommonException {
        Connection connection = localConnection.get();
        try {
            if (connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.warn("Unable to perform transaction committing.", e);
            throw new TransactionCommonException("Unable to perform transaction committing.", e);
        }
        localConnection.remove();
        return true;
    }


    @Override
    public boolean rollbackTransaction() {
        Connection connection = localConnection.get();
        try {
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.warn("Unable to perform transaction rollback.", e);
            throw new TransactionRollbackException("Unable to perform transaction rollback.", e);
        }
        localConnection.remove();
        return true;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = localConnection.get() != null ? localConnection.get() : connectionProvider.getConnection();

        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        LOGGER.info("Method close of proxy called.");
                        return null;
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }


}
