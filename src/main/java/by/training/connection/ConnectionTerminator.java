package by.training.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * Intended to check borrowed connections and release it by connection pool if
 * connection was taken more than {@code long msConnectionLifetime} ms ago.
 *
 * @author Uladzislau Stryzhonak
 */
public class ConnectionTerminator implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionTerminator.class);
    private final Map<Connection, Date> borrowed;
    private final long msConnectionLifetime;
    private final ConnectionPool pool;


    public ConnectionTerminator(Map<Connection, Date> borrowed, long msConnectionLifetime, ConnectionPool pool) {
        this.borrowed = borrowed;
        this.msConnectionLifetime = msConnectionLifetime;
        this.pool = pool;
    }


    @Override
    public void run() {

        long current = new Date().getTime();

        for (Map.Entry<Connection, Date> entry : borrowed.entrySet()) {

            long creation = entry.getValue().getTime();
            if (current - creation <= msConnectionLifetime) {
                continue;
            }

            try {
                Connection connection = entry.getKey();
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
                pool.release(connection);

            } catch (SQLException e) {
                LOGGER.error("Unable to close connection.", e);
            }
        }
    }


}
