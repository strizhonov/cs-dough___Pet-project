package by.training.deleted;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetManagerImpl implements ResultSetManager {

    private final ThreadLocal<ResultSet> localRs = new ThreadLocal<>();
    private int nestingLevel = -1;

    public ResultSetManagerImpl(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException("There's no data in result set.");
        }
        resultSet.previous();
        localRs.set(resultSet);
    }

    @Override
    public boolean start() throws SQLException {
        ++nestingLevel;

        if (nestingLevel != 0) {
            return hasCurrent();
        }

        if (hasNext()) {
            ResultSet resultSet = localRs.get();
            resultSet.next();
            return true;
        }

        return false;
    }

    @Override
    public void finish() throws SQLException {
        nestingLevel--;
    }

    @Override
    public ResultSet getResultSet() {
        return localRs.get();
    }

//    @Override
//    public Object getFromResultSet(String columnName) throws SQLException {
//        ResultSet rs = localRs.get();
//        return rs.getObject(columnName);
//    }

    private boolean hasCurrent() throws SQLException {
        ResultSet resultSet = localRs.get();
        return resultSet.getRow() != 0;
    }

    private boolean hasNext() throws SQLException {
        ResultSet resultSet = localRs.get();
        if (resultSet.isLast()) {
            return false;
        }
        boolean hasNext = resultSet.next();
        if (hasNext) {
            resultSet.previous();
        }
        return hasNext;
    }
}

