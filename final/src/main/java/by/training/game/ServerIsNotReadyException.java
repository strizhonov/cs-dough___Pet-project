package by.training.game;

public class ServerIsNotReadyException extends Exception {

    public ServerIsNotReadyException(String message) {
        super(message);
    }

    public ServerIsNotReadyException(String message, Throwable cause) {
        super(message, cause);
    }
}
