package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.User;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeUserLanguageToEnCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ChangeUserLanguageToEnCommand.class);
    private final ActionCommandType type = ActionCommandType.CHANGE_USER_LANGUAGE_TO_EN;
    private final UserService userService;

    public ChangeUserLanguageToEnCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
        userDto.setLanguage(User.Language.EN);
        try {
            userService.update(userDto);
            httpSession.setAttribute(AttributesContainer.LANGUAGE.toString(), AttributesContainer.EN.toString());
            return new HttpForwarder(PathsContainer.TO_USER_PAGE_COMMAND + userDto.getId());
        } catch (ServiceException e) {
            LOGGER.error("Language setting failed.", e);
            throw new ActionCommandExecutionException("Language setting failed.", e);
        }
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
