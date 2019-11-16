package by.training.controller;

public class TruckControllerException extends Exception {

    public TruckControllerException() {
    }

    public TruckControllerException(String message) {
        super(message);
    }

    public TruckControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruckControllerException(Throwable cause) {
        super(cause);
    }

    public TruckControllerException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
