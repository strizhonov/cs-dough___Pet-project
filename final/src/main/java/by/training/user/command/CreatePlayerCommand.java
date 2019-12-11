package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.common.ServiceException;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.user.PlayerDto;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.validation.PlayerDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CreatePlayerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreatePlayerCommand.class);
    private final ActionCommandType type = ActionCommandType.CREATE_PLAYER;
    private final UserService userService;
    //private final PlayerDataValidator validator;

    public CreatePlayerCommand(UserService userService) {
        this.userService = userService;
        //this.validator = new PlayerDataValidator(userService);
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            int i = -1;

            List<FileItem> items = sfu.parseRequest(request);
            byte[] photo = items.get(++i).get();
            if (photo == null || photo.length == 0) {
                File file = new File(request.getServletContext().getRealPath(PathsContainer.NOBODY));
                InputStream is = new FileInputStream(file);
                photo = IOUtils.toByteArray(is);
            }

            String name = items.get(++i).getString();
            String surname = items.get(++i).getString();
            String nickname = items.get(++i).getString();
            String country = items.get(++i).getString();

//            if (!isDataValid(nickname, validator, request)) {
//                return new ServletForwarder(servlet, request.getContextPath());
//            }

            HttpSession httpSession = request.getSession();
            UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
            PlayerDto playerDto = new PlayerDto.Builder()
                    .photo(photo)
                    .name(name)
                    .surname(surname)
                    .nickname(nickname)
                    .country(country)
                    .userId(userDto.getId())
                    .build();

            userService.createPlayer(playerDto);
            return new HttpForwarder(PathsContainer.SHOW_PLAYER + userDto.getPlayerAccountId());
        } catch (ServiceException | FileUploadException | IOException e) {
            LOGGER.error("Unable to perform player creation and user updating", e);
            throw new ActionCommandExecutionException("Unable to perform player creation and user updating", e);
        }


    }

    @Override
    public ActionCommandType getType() {
        return type;
    }

    private boolean isDataValid(String name, PlayerDataValidator validator, HttpServletRequest request)
            throws ActionCommandExecutionException {
        try {
            ValidationResult result = validator.validate(name);
            if (!result.isValid()) {
                setErrorAttributes(result.getValidationResult(), request);
                return false;
            }
            return true;
        } catch (ValidationException e) {
            LOGGER.error("Validation failed.", e);
            throw new ActionCommandExecutionException("Validation failed.", e);
        }
    }

    private void setErrorAttributes(Map<String, String> errorMap, HttpServletRequest request) {
        for (Map.Entry<String, String> entry : errorMap.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

}
