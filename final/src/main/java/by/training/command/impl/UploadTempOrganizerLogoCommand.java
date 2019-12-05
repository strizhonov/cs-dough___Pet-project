package by.training.command.impl;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.servlet.BlankRouter;
import by.training.servlet.HttpRouter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UploadTempOrganizerLogoCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.UPLOAD_TEMP_ORGANIZER_LOGO;

    @Override
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {
        byte[] logo = new byte[0];
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    logo = item.get();
                }
            }

            OutputStream os = new ByteArrayOutputStream();
            os.write(logo);
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return new BlankRouter();
    }

    @Override
    public ActionCommandType getType() {
        return type;
    }
}
