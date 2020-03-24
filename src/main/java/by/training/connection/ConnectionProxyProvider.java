package by.training.connection;

import by.training.core.Bean;
import by.training.resourse.AttributesContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Bean
public class ConnectionProxyProvider implements ConnectionProvider {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionProxyProvider.class);
    private final ConnectionPool connectionPool;


    public ConnectionProxyProvider(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool == null) {
            LOGGER.error("There is no instantiated pool of connections.");
            throw new SQLException("There is no instantiated pool of connections.");
        }

        Connection connection = connectionPool.getConnection();

        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (AttributesContainer.CLOSE.toString().equals(method.getName())) {
                        connectionPool.release(connection);
                        return null;
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }

}
