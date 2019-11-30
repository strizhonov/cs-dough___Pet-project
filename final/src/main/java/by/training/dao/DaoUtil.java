package by.training.dao;

class DaoUtil {

    static java.util.Date toUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

    static java.sql.Date toSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

}
