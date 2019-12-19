package by.training.connection;

import by.training.core.ApplicationContext;
import by.training.resourse.AppSetting;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class ConnectionPoolTest {


    private static ApplicationContext context;

    @BeforeClass
    public static void initContext() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        context = ApplicationContext.getInstance();
        Method init = context.getClass().getDeclaredMethod("init");
        init.setAccessible(true);
        init.invoke(context);
    }


    @Test
    public void singletonTest() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        for (int i = 0; i < 100; i++) {
            new SingletonRetrievingThread(connectionPool).start();
        }

        //Assert.succeed();
    }


    @Test
    public void initializingTest() throws NoSuchFieldException, IllegalAccessException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        for (int i = 0; i < 100; i++) {
            new PoolInitializingThread(i).start();
        }

        Field size = connectionPool.getClass().getDeclaredField("poolSize");
        size.setAccessible(true);
        int realSize = (Integer) size.get(connectionPool);


        AppSetting setting = (AppSetting) context.get(AppSetting.class);
        String sExpectedPoolSize = setting.get(AppSetting.SettingName.CONNECTION_POOL_SIZE);

        Assert.assertEquals(Integer.parseInt(sExpectedPoolSize), realSize);
    }


    private static class SingletonRetrievingThread extends Thread {

        private ConnectionPool connectionPool;
        public SingletonRetrievingThread(ConnectionPool connectionPool) {
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


    private static class PoolInitializingThread extends Thread {
        private int size;
        public PoolInitializingThread(int size) {
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
