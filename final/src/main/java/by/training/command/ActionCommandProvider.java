package by.training.command;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommandProvider {
    ActionCommand get(HttpServletRequest request);

    void register(ActionCommand... actionCommands);
}
