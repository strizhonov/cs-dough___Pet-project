package by.training.security;

import by.training.command.ActionCommand;
import by.training.servlet.ServletRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForUserOwnerAccessSupervisor implements AccessChecker, AccessRouter {

    @Override
    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
        String sPlayerId = request.getParameter(AttributesContainer.PLAYER_ID.toString());
        long playerId = sPlayerId == null ? null : Long.parse(sPlayerId);
        
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
        PlayerDto playerToOperate = (PlayerDto) session.getAttribute(AttributesContainer.PLAYER_TO_OPERATE.toString());
        return user != null && (user.getPlayerId() == playerToOperate.getId() || user.getPlayerId() == playerId);
        }
    
}