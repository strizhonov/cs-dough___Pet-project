package by.training.common;

import by.training.connection.TransactionManager;

public abstract class BaseBeanService {

    protected TransactionManager transactionManager;

    public BaseBeanService(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
