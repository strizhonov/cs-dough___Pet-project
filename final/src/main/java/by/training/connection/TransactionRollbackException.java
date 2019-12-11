package by.training.connection;

public class TransactionRollbackException extends RuntimeException {

    public TransactionRollbackException(String message) {
        super(message);
    }

    public TransactionRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

}
