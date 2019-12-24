package by.training.connection;

import by.training.resourse.AppSetting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;


public class TransactionManagerTest {

    @Test
    public void integral() throws SQLException, NoSuchFieldException, IllegalAccessException, InterruptedException {

        ConnectionPool connectionPool = new ConnectionPool(15);

        ConnectionProvider proxyProvider = new ConnectionProxyProvider(connectionPool);
        TransactionManager transactionManager = new TransactionManagerImpl(proxyProvider);

        for (int i = 0; i < 100; i++) {
            Thread thread = new TransactionTestingThread(transactionManager);
            thread.start();
        }

    }


    @RunWith(JUnit4.class)
    private static final class TransactionTestingThread extends Thread {

        private TransactionManager transactionManager;

        public TransactionTestingThread(TransactionManager transactionManager) {
            this.transactionManager = transactionManager;
        }

        @Test
        @Override
        @SuppressWarnings("unchecked")
        public void run() {
            try {
                transactionManager.startTransaction();
                Random random = new Random();
                Thread.sleep(random.nextInt(1000));

                Field fLocalConnection = TransactionManagerImpl.class.getDeclaredField("localConnection");
                fLocalConnection.setAccessible(true);
                ThreadLocal<Connection> localConnection = (ThreadLocal<Connection>) fLocalConnection.get(transactionManager);
                Connection connectionFromStarting = localConnection.get();

                Connection getConnectionFromGetting = ((ConnectionProvider) transactionManager).getConnection();

                Assert.assertEquals(getConnectionFromGetting, connectionFromStarting);


                if (random.nextBoolean()) {
                    transactionManager.commitTransaction();
                } else {
                    transactionManager.rollbackTransaction();
                }

            } catch (TransactionException | InterruptedException | SQLException | NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException("Should not be here");
            }
        }
    }


}
