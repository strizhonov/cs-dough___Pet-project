package by.training.security;

import by.training.command.ActionCommand;

public interface SecurityProvider<T> {

    SecurityDirector<T> get(ActionCommand command);

    void register(SecurityDirector<T> supervisor);

}
