package by.training.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DateConverterTest {


    @Test
    public void toUtilDate() {
        Class<?> clazz = DateConverter.toUtilDate(new java.sql.Date(0)).getClass();
        Assert.assertEquals(clazz, java.util.Date.class);
    }

    @Test
    @SuppressWarnings("all")
    public void toUtilDateNull() {
        java.util.Date utilDate = DateConverter.toUtilDate(null);
        Assert.assertNull(utilDate);
    }

    @Test
    public void toSqlDate() {
        Class<?> clazz = DateConverter.toSqlDate(new java.util.Date(0)).getClass();
        Assert.assertEquals(clazz, java.sql.Date.class);
    }

    @Test
    @SuppressWarnings("all")
    public void toSqlDateNull() {
        java.sql.Date sqlDate = DateConverter.toSqlDate(null);
        Assert.assertNull(sqlDate);
    }


}
