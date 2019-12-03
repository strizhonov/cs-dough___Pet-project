package by.training.security;

import by.training.command.ActionCommand;

public interface SecurityProvider<T> {

    SecuritySupervisor<T> get(ActionCommand command);

    void register(SecuritySupervisor<T> supervisor);

}
