package by.training.security;

import by.training.command.ActionCommand;

public interface SecurityProvider {

    SecurityDirector get(ActionCommand command);

    void register(SecurityDirector... supervisors);

}
