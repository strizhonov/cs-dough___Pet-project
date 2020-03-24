package by.training.connection;

import by.training.core.ApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(JUnit4.class)
public class ConnectionProxyProviderTest {


    @Test
    @SuppressWarnings("unchecked")
    public void getConnection() throws SQLException, NoSuchFieldException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {

        ApplicationContext context = ApplicationContext.getInstance();
        Method init = context.getClass().getDeclaredMethod("init");
        init.setAccessible(true);
        init.invoke(context);

        ConnectionPool pool = (ConnectionPool) context.get(ConnectionPool.class);
        ConnectionProxyProvider proxyProvider = new ConnectionProxyProvider(pool);

        Field fPoolSize = pool.getClass().getDeclaredField("poolSize");
        fPoolSize.setAccessible(true);
        int poolSize = (Integer) fPoolSize.get(pool);

        for (int i = 0; i < poolSize - 1; i++) {
            proxyProvider.getConnection();
        }

        Connection lastConnection = proxyProvider.getConnection();
        Assert.assertNotNull(lastConnection);

        Field fAvailable = pool.getClass().getDeclaredField("available");
        fAvailable.setAccessible(true);
        List<Connection> available = (List<Connection>) fAvailable.get(pool);
        Assert.assertTrue(available.isEmpty());

        lastConnection.close();
        Assert.assertEquals(1, available.size());


    }


}
