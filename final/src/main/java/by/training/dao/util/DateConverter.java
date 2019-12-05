package by.training.dao.util;

public class DateConverter {

    public static java.util.Date toUtilDate(java.sql.Date sqlDate) {
        return sqlDate == null
        ? null
        : new java.util.Date(sqlDate.getTime());
    }

    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        return utilDate == null
                ? null
                : new java.sql.Date(utilDate.getTime());
    }

}
