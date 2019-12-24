package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GetTournamentLogoCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(GetTournamentLogoCommand.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final ActionCommandType type = ActionCommandType.GET_TOURNAMENT_LOGO;
    private final TournamentService service;


    public GetTournamentLogoCommand(TournamentService service) {
        this.service = service;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        String
                id = request.getParameter(AttributesContainer.ID.toString());
        try {

            TournamentDto tournament = service.find(Long.parseLong(id));
            byte[] logo = tournament.getLogo();

            response.setContentType(setting.get(AppSetting.SettingName.IMAGE_FORMAT));
            response.getOutputStream().write(logo);
            response.getOutputStream().flush();

            return Optional.empty();

        } catch (ServiceException | IOException e) {
            LOGGER.error("Logo retrieving failed.", e);
            throw new ActionCommandExecutionException("Logo retrieving failed.", e);
        }

    }


}
