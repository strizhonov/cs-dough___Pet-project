package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.entity.GameDto;
import by.training.service.GameService;
import by.training.service.ServiceException;
import by.training.servlet.ServletRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToGamePageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ToGamePageCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_GAME_PAGE;
    private GameService gameService;

    public ToGamePageCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String sId = request.getParameter(ApplicationConstantsContainer.ID);
        long id = Long.parseLong(sId);
        try {
            GameDto gameDto = gameService.get(id);
            request.setAttribute(ApplicationConstantsContainer.GAME, gameDto);
            return new ServletRouter("/jsp/game-page.jsp");
        } catch (ServiceException e) {
            LOGGER.error("Unable to get game with " + id + " id.", e);
            throw new ActionCommandExecutionException("Unable to get game with " + id + " id.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
