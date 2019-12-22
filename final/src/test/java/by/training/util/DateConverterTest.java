package by.training.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Timestamp;

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
        java.util.Date utilDate = DateConverter.toUtilDate((Timestamp) null);
        Assert.assertNull(utilDate);
    }

    @Test
    public void toSqlDate() {
        Class<?> clazz = DateConverter.toTimeStamp(new java.util.Date(0)).getClass();
        Assert.assertEquals(clazz, java.sql.Date.class);
    }

    @Test
    @SuppressWarnings("all")
    public void toSqlDateNull() {
        java.sql.Timestamp sqlDate = DateConverter.toTimeStamp(null);
        Assert.assertNull(sqlDate);
    }


}
