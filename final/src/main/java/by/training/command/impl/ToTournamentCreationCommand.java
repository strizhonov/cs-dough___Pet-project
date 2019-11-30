package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToTournamentCreationCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_TOURNAMENT_CREATION;

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        return new ServletRouter("/jsp/tournament-creation.jsp");
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }

}
