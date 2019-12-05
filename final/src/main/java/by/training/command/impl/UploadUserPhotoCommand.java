package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.ValuesContainer;
import by.training.dto.UserDto;
import by.training.service.ServiceException;
import by.training.service.UserService;
import by.training.servlet.HttpRouter;
import by.training.servlet.RelativePathRedirector;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UploadUserPhotoCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.UPLOAD_USER_PHOTO;

    private UserService userService;

    public UploadUserPhotoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
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
            return new RelativePathRedirector("/?command=to_user_page&id=" + current.getId());
        } catch (ServiceException | FileUploadException e) {
            throw new ActionCommandExecutionException(e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
