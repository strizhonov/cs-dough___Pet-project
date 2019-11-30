package by.training.connection;

public interface TransactionManager extends ConnectionProvider {

    boolean startTransaction() throws TransactionCommonException;

    boolean commitTransaction() throws TransactionCommonException;

    boolean rollbackTransaction();

}