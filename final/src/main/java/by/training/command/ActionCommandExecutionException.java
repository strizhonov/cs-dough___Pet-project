package by.training.command;

public class ActionCommandExecutionException extends Exception {

    public ActionCommandExecutionException(String message) {
        super(message);
    }

    public ActionCommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
