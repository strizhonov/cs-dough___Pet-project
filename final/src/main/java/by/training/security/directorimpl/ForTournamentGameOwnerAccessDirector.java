package by.training.security.directorimpl;

import by.training.core.ServiceException;
import by.training.game.Game;
import by.training.game.GameService;
import by.training.game.PlainGameDto;
import by.training.organizer.OrganizerDto;
import by.training.organizer.OrganizerService;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.security.AccessAllowedForType;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.tournament.Tournament;
import by.training.tournament.TournamentService;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ForTournamentGameOwnerAccessDirector extends BaseSecurityDirector {

    private static final Logger LOGGER = LogManager.getLogger(ForTournamentGameOwnerAccessDirector.class);
    private static final String REDIRECT_TO = PathsContainer.FILE_ACCESS_DENIED;
    private final AccessAllowedForType type = AccessAllowedForType.TOURNAMENT_GAME_OWNER;
    private final OrganizerService organizerService;
    private final GameService gameService;

    public ForTournamentGameOwnerAccessDirector(OrganizerService organizerService, GameService gameService) {
        this.organizerService = organizerService;
        this.gameService = gameService;
    }

    @Override
    public AccessAllowedForType getType() {
        return type;
    }


    @Override
    public boolean isAccessAllowed(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(AttributesContainer.USER.toString());

        String sGameId = request.getParameter(AttributesContainer.ID.toString());
        long gameId = Long.parseLong(sGameId);

        try {

            PlainGameDto game = gameService.find(gameId);

            OrganizerDto organizer = organizerService.find(user.getOrganizerId());

            return organizer.getTournamentsIdList().contains(game.getTournamentId());

        } catch (ServiceException e) {
            LOGGER.error("Unable to get organizer", e);
            return false;
        }

    }


    @Override
    protected Optional<HttpRouter> getHttpRouter(HttpServletRequest request, HttpServletResponse response) {
        return Optional.of(new HttpRedirector(request.getContextPath() + REDIRECT_TO));
    }

}