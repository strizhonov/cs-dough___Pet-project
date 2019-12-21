package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.util.ServletUtil;
import by.training.validation.UserDataValidator;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class UploadUserPhotoCommand implements ActionCommand {


    private static final Logger LOGGER = LogManager.getLogger(UploadUserPhotoCommand.class);
    private final ActionCommandType type = ActionCommandType.UPLOAD_USER_PHOTO;
    private final UserService userService;


    public UploadUserPhotoCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        try {

            List<FileItem> items = ServletUtil.parseRequest(request);

            long avatarSize = items.get(0).getSize();


            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (String) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));


            UserDataValidator validator = new UserDataValidator(userService, manager);


            ValidationResult result = validator.avatarSize(avatarSize);
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(result.getFirstValue()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_TOURNAMENT_CREATION_PAGE));
            }

            byte[] avatar = items.get(0).get();

            HttpSession httpSession = request.getSession();
            UserDto user = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());
            user.setAvatar(avatar);


            userService.update(user);

            return Optional.of(new HttpRedirector( request.getContextPath()
                    + PathsContainer.FILE_USER_PROFILE_PAGE));

        } catch (ServiceException | FileUploadException e) {
            LOGGER.error("Avatar updating failed.", e);
            throw new ActionCommandExecutionException("Avatar updating failed.", e);
        }


    }




}
