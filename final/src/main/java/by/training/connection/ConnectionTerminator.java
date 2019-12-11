package by.training.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class ConnectionTerminator implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionTerminator.class);
    private final Map<Connection, Date> borrowed;
    private final long msConnectionLifetime;

    public ConnectionTerminator(Map<Connection, Date> borrowed, long msConnectionLifetime) {
        this.borrowed = borrowed;
        this.msConnectionLifetime = msConnectionLifetime;
    }

    @Override
    public void run() {
        long current = new Date().getTime();

        Iterator<Map.Entry<Connection, Date>> iterator = borrowed.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Connection, Date> entry = iterator.next();
            long creation = entry.getValue().getTime();

            if (current - creation <= msConnectionLifetime) {
                continue;
            }

            try {
                Connection connection = entry.getKey();
                connection.rollback();
                connection.close();
                iterator.remove();
            } catch (SQLException e) {
                LOGGER.error("Unable to close connection.", e);
            }
        }
    }


}
