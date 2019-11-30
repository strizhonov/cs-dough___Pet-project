package by.training.connection;

public class TransactionRollbackException extends RuntimeException {

    public TransactionRollbackException() {
    }

    public TransactionRollbackException(String message) {
        super(message);
    }

    public TransactionRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionRollbackException(Throwable cause) {
        super(cause);
    }

    public TransactionRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
