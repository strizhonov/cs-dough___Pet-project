package by.training.util;

public class DateConverter {


    public static java.util.Date toUtilDate(java.sql.Date sqlDate) {
        return sqlDate == null
                ? null
                : new java.util.Date(sqlDate.getTime());
    }


    public static java.util.Date toUtilDate(java.sql.Timestamp timestamp) {
        return timestamp == null
                ? null
                : new java.util.Date(timestamp.getTime());
    }


    public static java.sql.Timestamp toTimeStamp(java.util.Date utilDate) {
        return utilDate == null
                ? null
                : new java.sql.Timestamp(utilDate.getTime());
    }

}
