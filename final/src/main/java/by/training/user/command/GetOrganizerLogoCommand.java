package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.core.AppSetting;
import by.training.core.ApplicationContext;
import by.training.servlet.HttpRespondent;
import by.training.servlet.HttpRouter;
import by.training.user.OrganizerDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetOrganizerLogoCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(GetOrganizerLogoCommand.class);
    private final ActionCommandType type = ActionCommandType.GET_ORGANIZER_LOGO;
    private final UserService userService;

    public GetOrganizerLogoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String id = request.getParameter(AttributesContainer.ID.toString());
        try {
            OrganizerDto organizerDto = userService.findOrganizer(Long.parseLong(id));
            byte[] logo = organizerDto.getLogo();

            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
            response.setContentType(setting.get(AppSetting.SettingName.IMAGE_FORMAT));
            response.getOutputStream().write(logo);
            response.getOutputStream().flush();
            return new HttpRespondent();
        } catch (ServiceException | IOException e) {
            LOGGER.error("Organizer logo retrieving failed.", e);
            throw new ActionCommandExecutionException("Organizer logo retrieving failed.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
