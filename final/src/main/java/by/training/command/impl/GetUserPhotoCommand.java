package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.ValuesContainer;
import by.training.dto.UserDto;
import by.training.service.ServiceException;
import by.training.service.UserService;
import by.training.servlet.BlankRouter;
import by.training.servlet.HttpRouter;
import by.training.servlet.RelativePathRedirector;
import by.training.servlet.ServletForwarder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetUserPhotoCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.GET_USER_PHOTO;

    private UserService userService;

    public GetUserPhotoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        String id = request.getParameter("id");
        try {
            UserDto userDto = userService.find(Long.parseLong(id));
            byte[] avatar = userDto.getAvatar();

            response.setContentType("image/jpg");
            response.getOutputStream().write(avatar);
            response.getOutputStream().flush();
            return new BlankRouter();
        } catch (ServiceException | IOException e) {
            throw new ActionCommandExecutionException(e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
