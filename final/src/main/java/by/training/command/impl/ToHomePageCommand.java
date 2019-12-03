package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.ValuesContainer;
import by.training.dto.GameDto;
import by.training.service.GameService;
import by.training.service.ServiceException;
import by.training.servlet.HttpRouter;
import by.training.servlet.ServletForwarder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ToHomePageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_HOME_PAGE;
    private GameService gameService;

    public ToHomePageCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        try {
            List<GameDto> latestGames = gameService.findLatest(ValuesContainer.HOME_PAGE_MAX_GAME_RESULTS);
            request.setAttribute(AttributesContainer.LATEST_GAMES.toString(), latestGames);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get games' list.", e);
            throw new ActionCommandExecutionException("Unable to get games' list.", e);
        }
        return new ServletForwarder(servlet, "/jsp/home.jsp");
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
