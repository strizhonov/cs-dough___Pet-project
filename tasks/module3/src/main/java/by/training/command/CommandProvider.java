package by.training.command;

public interface CommandProvider<T> {
    Command<T> get(CommandType type);

    void register(CommandType type, Command<T> command);
}
