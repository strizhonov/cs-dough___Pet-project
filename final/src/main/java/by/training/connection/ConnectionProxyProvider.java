package by.training.connection;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProxyProvider implements ConnectionProvider {

    private ConnectionPool connectionPool;

    public ConnectionProxyProvider(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool != null) {
            Connection connection = connectionPool.getConnection();
            return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if ("close".equals(method.getName())) {
                            connectionPool.release(connection);
                            return null;
                        } else {
                            return method.invoke(connection, args);
                        }
                    });
        }

        throw new SQLException("There is no instantiated pool of connections.");
    }

}
