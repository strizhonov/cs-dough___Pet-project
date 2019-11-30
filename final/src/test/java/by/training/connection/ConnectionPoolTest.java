package by.training.connection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class ConnectionPoolTest {

    @Test
    public void singletonTest() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        for (int i = 0; i < 100; i++) {
            new GetSingletonThread(connectionPool).start();
        }
    }

    @Test
    public void initializingTest() throws SQLException, NoSuchFieldException, IllegalAccessException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        int initSize = 5;
        connectionPool.init(initSize);
        for (int i = 0; i < 100; i++) {
            new InitThread(i).start();
        }
        Field size = connectionPool.getClass().getDeclaredField("poolSize");
        size.setAccessible(true);
        int realSize = (Integer) size.get(connectionPool);

        Assert.assertEquals(initSize, realSize);
    }

    private static class GetSingletonThread extends Thread {
        private ConnectionPool connectionPool;
        public GetSingletonThread(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
        }
        @Override
        public void run() {
            ConnectionPool toCheck = ConnectionPool.getInstance();
            if (toCheck != connectionPool) {
                throw new IllegalStateException("Singleton impl is wrong.");
            }
        }
    }

    private static class InitThread extends Thread {
        private int size;
        public InitThread(int size) {
            this.size = size;
        }
        @Override
        public void run() {
            ConnectionPool toCheck = ConnectionPool.getInstance();
            try {
                toCheck.init(size);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
