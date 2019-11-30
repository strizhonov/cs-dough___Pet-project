package by.training.connection;

public class TransactionCommonException extends Exception {

    public TransactionCommonException() {
    }

    public TransactionCommonException(String message) {
        super(message);
    }

    public TransactionCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionCommonException(Throwable cause) {
        super(cause);
    }

    public TransactionCommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
