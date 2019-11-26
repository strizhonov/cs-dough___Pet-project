package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandType;
import by.training.service.PlayerService;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinTournamentCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.JOIN_TOURNAMENT;
    private PlayerService playerService;

    public JoinTournamentCommand(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) {
        String sPlayerId = request.getParameter(ApplicationConstantsContainer.PLAYER_ID);
        long playerId = Long.parseLong(sPlayerId);
        String sTournamentId = request.getParameter(ApplicationConstantsContainer.TOURNAMENT_ID);
        long tournamentId = Long.parseLong(sTournamentId);
        playerService.join(playerId, tournamentId);
        return new ServletRouter("/jsp/tournament-page.jsp");
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
