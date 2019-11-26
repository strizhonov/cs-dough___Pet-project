package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToOrganizerCreationCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_ORGANIZER_CREATION;

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        return new ServletRouter("/jsp/organizer-creation.jsp", ServletRouter.RouterType.REDIRECT);
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
