package controller;

public class WarehouseControllerException extends Exception {

    public WarehouseControllerException() {
    }

    public WarehouseControllerException(String message) {
        super(message);
    }

    public WarehouseControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarehouseControllerException(Throwable cause) {
        super(cause);
    }

    public WarehouseControllerException(String message, Throwable cause,
                                        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
