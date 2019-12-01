package by.training.connection;

public interface TransactionManager {

    boolean startTransaction() throws TransactionCommonException;

    boolean commitTransaction() throws TransactionCommonException;

    boolean rollbackTransaction();

}