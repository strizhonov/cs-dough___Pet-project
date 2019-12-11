package by.training.connection;

public interface TransactionManager {

    void startTransaction() throws TransactionException;

    void commitTransaction() throws TransactionException;

    void rollbackTransaction();

}