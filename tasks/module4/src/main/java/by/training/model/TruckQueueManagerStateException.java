package by.training.model;

public class TruckQueueManagerStateException extends Exception {

    public TruckQueueManagerStateException() {
    }

    public TruckQueueManagerStateException(String message) {
        super(message);
    }

    public TruckQueueManagerStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruckQueueManagerStateException(Throwable cause) {
        super(cause);
    }

    public TruckQueueManagerStateException(String message, Throwable cause,
                                           boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
