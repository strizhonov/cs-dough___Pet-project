package by.training.tournament;

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
import by.training.user.NotEnoughFundsException;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public class JoinTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(JoinTournamentCommand.class);
    private final ActionCommandType type = ActionCommandType.JOIN_TOURNAMENT;
    private final TournamentService tournamentService;


    public JoinTournamentCommand(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());
        long playerId = user.getPlayerAccountId();

        String sTournamentId = request.getParameter(AttributesContainer.ID.toString());
        long tournamentId = Long.parseLong(sTournamentId);

        ParticipantDto dto = new ParticipantDto(playerId, tournamentId);

        try {

            if (tournamentService.joinTournament(dto)) {

                return Optional.of(new HttpForwarder(PathsContainer.COMMAND_TO_TOURNAMENT_PAGE + tournamentId));

            } else {

                return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_ERROR_PAGE));
            }

        } catch (NotEnoughFundsException e) {
            LOGGER.error("Not enough funds.", e);
            LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                    (Locale) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));


            request.setAttribute(AttributesContainer.MESSAGE.toString(),
                    manager.getValue(AttributesContainer.NOT_ENOUGH_FUNDS.toString()));

            return Optional.of(new HttpForwarder(PathsContainer.COMMAND_TO_TOURNAMENT_PAGE + tournamentId));

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform tournament joining.", e);
            throw new ActionCommandExecutionException("Unable to perform tournament joining.", e);
        }

    }


}
