package by.training.common;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.core.AppSetting;
import by.training.core.ApplicationContext;
import by.training.game.GameDto;
import by.training.game.GameService;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.user.command.CreateOrganizerCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ToHomePageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizerCommand.class);
    private final ActionCommandType type = ActionCommandType.TO_HOME_PAGE;
    private final GameService gameService;

    public ToHomePageCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
        try {
            String sMaxResults = setting.get(AppSetting.SettingName.HOME_PAGE_MAX_GAME_RESULTS);
            List<GameDto> latestGames = gameService.findLatest(Integer.parseInt(sMaxResults));
            request.setAttribute(AttributesContainer.LATEST_GAMES.toString(), latestGames);
            return new HttpForwarder(PathsContainer.HOME);
        } catch (ServiceException e) {
            LOGGER.error("Unable to get games' list.", e);
            throw new ActionCommandExecutionException("Unable to get games' list.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
