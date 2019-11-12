package model;

public class TruckQueueManagerException extends Exception {

    public TruckQueueManagerException() {
    }

    public TruckQueueManagerException(String message) {
        super(message);
    }

    public TruckQueueManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruckQueueManagerException(Throwable cause) {
        super(cause);
    }

    public TruckQueueManagerException(String message, Throwable cause,
                                      boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
