package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.entity.Game;
import by.training.entity.TournamentDto;
import by.training.entity.UserDto;
import by.training.service.ServiceException;
import by.training.service.TournamentService;
import by.training.servlet.ServletRouter;
import by.training.validation.TournamentDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CreateTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateTournamentCommand.class);
    private final ActionCommandType type = ActionCommandType.CREATE_TOURNAMENT;
    private TournamentService tournamentService;
    private TournamentDataValidator validator;

    public CreateTournamentCommand(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
        this.validator = new TournamentDataValidator(tournamentService);
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String name = request.getParameter(ApplicationConstantsContainer.NAME);
        if (!isDataValid(name, validator, request)) {
            return new ServletRouter(request.getContextPath());
        }

        TournamentDto tournamentDto = compile(request);
        try {
            long tournamentId = tournamentService.save(tournamentDto);
            return new ServletRouter("/app?command=show_tournament&id=" + tournamentId);
        } catch (ServiceException e) {
            LOGGER.error("Tournament creation failed.", e);
            throw new ActionCommandExecutionException("Tournament creation failed.", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }

    private boolean isDataValid(String name, TournamentDataValidator validator, HttpServletRequest request)
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

    private TournamentDto compile(HttpServletRequest request) {
        String name = request.getParameter(ApplicationConstantsContainer.NAME);
        //byte[] logo = request.getParameter(ApplicationConstantContainer.LOGO);
        String sPrizePool = request.getParameter(ApplicationConstantsContainer.PRIZE_POOL);
        double prizePool = Double.parseDouble(sPrizePool);
        String sPlayersNumber = request.getParameter(ApplicationConstantsContainer.PLAYERS_NUMBER);
        int playersNumber = Integer.parseInt(sPlayersNumber);
        String sStartDate = request.getParameter(ApplicationConstantsContainer.START_DATE);
        String sEndDate = request.getParameter(ApplicationConstantsContainer.END_DATE);
        Date startDate = new Date();
        try {
            startDate = new SimpleDateFormat(ApplicationConstantsContainer.SIMPLE_DATE_FORMAT).parse(sStartDate);
        } catch (ParseException e) {
            LOGGER.warn("Start date parsing failed.", e);
        }
        Date endDate = new Date();
        try {
            endDate = new SimpleDateFormat(ApplicationConstantsContainer.SIMPLE_DATE_FORMAT).parse(sEndDate);
        } catch (ParseException e) {
            LOGGER.warn("End date parsing failed.", e);
        }
        String sGamesType = request.getParameter(ApplicationConstantsContainer.GAME_TYPE);
        Game.GameType commonGameType = Game.GameType.fromString(sGamesType).orElse(Game.GameType.BO3);

        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(ApplicationConstantsContainer.USER);

        return TournamentDto.Builder.aTournamentDto()
                .name(name)
                //  .logo(logo)
                .prizePool(prizePool)
                .playersNumber(playersNumber)
                .startDate(startDate)
                .endDate(endDate)
                .commonGameType(commonGameType)
                .organizerId(userDto.getOrganizerId())
                .build();
    }

}
