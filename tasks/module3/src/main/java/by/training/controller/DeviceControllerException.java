package by.training.controller;

public class DeviceControllerException extends Exception {

    public DeviceControllerException() {
    }

    public DeviceControllerException(String message) {
        super(message);
    }

    public DeviceControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceControllerException(Throwable cause) {
        super(cause);
    }

    protected DeviceControllerException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
