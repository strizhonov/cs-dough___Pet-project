package by.training.command;

import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.game.ComplexGameDto;
import by.training.game.GameService;
import by.training.organizer.CreateOrganizerCommand;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ToHomePageCommand implements ActionCommand {


    private static final Logger LOGGER = LogManager.getLogger(CreateOrganizerCommand.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final ActionCommandType type = ActionCommandType.TO_HOME_PAGE;
    private final GameService gameService;


    public ToHomePageCommand(GameService gameService) {
        this.gameService = gameService;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            String sMaxResults = setting.get(AppSetting.SettingName.HOME_PAGE_MAX_GAME_RESULTS);
            List<ComplexGameDto> latestGames = gameService.findLatest(Integer.parseInt(sMaxResults));
            request.setAttribute(AttributesContainer.LATEST_GAMES.toString(), latestGames);

            return Optional.of(new HttpForwarder(PathsContainer.FILE_HOME));

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
