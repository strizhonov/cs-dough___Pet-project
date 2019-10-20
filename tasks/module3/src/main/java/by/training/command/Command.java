package by.training.command;

import java.util.List;

public interface Command<T> {
    List<T> execute(String path) throws CommandException;
}
