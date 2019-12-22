package by.training.player;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.organizer.ToOrganizerEditingCommand;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.tournament.TournamentDto;
import by.training.tournament.TournamentService;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created for security reasons as security service filters only request with "command" parameter
 */
public class ToPlayerCreationCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_PLAYER_CREATION;

    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_PLAYER_CREATION));

    }

}
