package by.training.service;

public class TruckServiceException extends Exception {

    public TruckServiceException() {
    }

    public TruckServiceException(String message) {
        super(message);
    }

    public TruckServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruckServiceException(Throwable cause) {
        super(cause);
    }

    public TruckServiceException(String message, Throwable cause,
                                 boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
