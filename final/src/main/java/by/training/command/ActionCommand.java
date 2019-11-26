package by.training.command;

import by.training.service.UserServiceException;
import by.training.servlet.ServletRouter;
import org.apache.commons.codec.DecoderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;

public interface ActionCommand {
    ServletRouter execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, UserServiceException, IOException, GeneralSecurityException, DecoderException, ParseException;

    ActionCommandType getType();
}
