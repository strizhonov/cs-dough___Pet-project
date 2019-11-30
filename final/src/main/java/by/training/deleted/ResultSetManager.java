package by.training.deleted;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetManager {

    boolean start() throws SQLException;

    void finish() throws SQLException;

    ResultSet getResultSet();
}
