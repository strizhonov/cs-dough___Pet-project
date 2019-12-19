package by.training.connection;

import by.training.core.ApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class ConnectionTerminatorTest {


    @Test
    public void integral() throws InterruptedException, SQLException {

        Map<Connection, Date> borrowed = new HashMap<>();

        for (int i = 0; i < 15; i++) {
            Connection connection = Mockito.mock(Connection.class);
            Mockito.when(connection.getAutoCommit()).thenReturn(false);
            Date date = new Date();
            borrowed.put(connection, date);
        }

        Assert.assertEquals(15, borrowed.size());

        Thread.sleep(100);

        ConnectionTerminator terminator = new ConnectionTerminator(borrowed, 0);
        Thread thread = new Thread(terminator);
        thread.start();
        thread.join();

        Assert.assertEquals(0, borrowed.size());

    }


}
