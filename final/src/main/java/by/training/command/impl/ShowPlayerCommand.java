package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.entity.PlayerDto;
import by.training.service.PlayerService;
import by.training.service.ServiceException;
import by.training.servlet.ServletRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPlayerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowPlayerCommand.class);
    private final ActionCommandType type = ActionCommandType.SHOW_PLAYER;
    private PlayerService playerService;

    public ShowPlayerCommand(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String sId = request.getParameter(ApplicationConstantsContainer.ID);
        long id = Long.parseLong(sId);
        try {
            PlayerDto playerDto = playerService.get(id);
            request.setAttribute(ApplicationConstantsContainer.PLAYER, playerDto);
            return new ServletRouter("/jsp/player-profile.jsp");
        } catch (ServiceException e) {
            LOGGER.error("Unable to get player with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get player with " + id + " id.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
