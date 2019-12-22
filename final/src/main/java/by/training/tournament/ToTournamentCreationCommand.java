package by.training.tournament;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.organizer.OrganizerDto;
import by.training.organizer.OrganizerService;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpForwarder;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created for security reasons as security service filters only request with "command" parameter
 */
public class ToTournamentCreationCommand implements ActionCommand {

    private final ActionCommandType type = ActionCommandType.TO_TOURNAMENT_CREATION;


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_TOURNAMENT_CREATION_PAGE));

    }

}
