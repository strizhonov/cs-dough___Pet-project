package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.common.ServiceException;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpRedirector;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.*;
import java.util.List;

public class UploadUserPhotoCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UploadUserPhotoCommand.class);
    private final ActionCommandType type = ActionCommandType.UPLOAD_USER_PHOTO;
    private final UserService userService;

    public UploadUserPhotoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        try {
            byte[] avatar = null;
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(factory);
            List<FileItem> items = sfu.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    avatar = item.get();
                }
            }

            HttpSession session = request.getSession();
            UserDto current = (UserDto) session.getAttribute(AttributesContainer.USER.toString());
            current.setAvatar(avatar);
            userService.update(current);
            return new HttpForwarder(PathsContainer.TO_USER_PAGE_COMMAND + current.getId());
        } catch (ServiceException | FileUploadException e) {
            LOGGER.error("Avatar updating failed.", e);
            throw new ActionCommandExecutionException("Avatar updating failed.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
