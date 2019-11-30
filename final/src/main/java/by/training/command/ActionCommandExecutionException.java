package by.training.command;

public class ActionCommandExecutionException extends Exception {

    public ActionCommandExecutionException() {
    }

    public ActionCommandExecutionException(String message) {
        super(message);
    }

    public ActionCommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionCommandExecutionException(Throwable cause) {
        super(cause);
    }

    public ActionCommandExecutionException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
