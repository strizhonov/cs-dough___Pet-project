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
import by.training.user.PlayerDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetPlayerPhotoCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(GetPlayerPhotoCommand.class);
    private final ActionCommandType type = ActionCommandType.GET_PLAYER_PHOTO;
    private final UserService userService;

    public GetPlayerPhotoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String id = request.getParameter(AttributesContainer.ID.toString());
        try {
            PlayerDto playerDto = userService.findPlayer(Long.parseLong(id));
            byte[] photo = playerDto.getPhoto();

            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
            response.setContentType(setting.get(AppSetting.SettingName.IMAGE_FORMAT));
            response.getOutputStream().write(photo);
            response.getOutputStream().flush();

            return new HttpRespondent();
        } catch (ServiceException | IOException e) {
            LOGGER.error("Player photo retrieving failed.", e);
            throw new ActionCommandExecutionException("Player photo retrieving failed.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
