package by.training.command.impl;


import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.ValuesContainer;
import by.training.dto.TournamentDto;
import by.training.dto.UserDto;
import by.training.entity.Game;
import by.training.service.ServiceException;
import by.training.service.TournamentService;
import by.training.servlet.HttpRouter;
import by.training.servlet.ServletForwarder;
import by.training.validation.TournamentDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
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
    public HttpRouter direct(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String name = request.getParameter(AttributesContainer.NAME.toString());
        if (!isDataValid(name, validator, request)) {
            return new ServletForwarder(servlet, request.getContextPath());
        }

        TournamentDto tournamentDto = compile(request);
        try {
            long tournamentId = tournamentService.create(tournamentDto);
            return new ServletForwarder(servlet, "/?command=show_tournament&id=" + tournamentId);
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
        String name = request.getParameter(AttributesContainer.NAME.toString());
        //byte[] logo = request.getParameter(ApplicationConstantContainer.LOGO);
        String sPrizePool = request.getParameter(AttributesContainer.PRIZE_POOL.toString());
        double prizePool = Double.parseDouble(sPrizePool);
        String sPlayersNumber = request.getParameter(AttributesContainer.PLAYERS_NUMBER.toString());
        int playersNumber = Integer.parseInt(sPlayersNumber);
        String sStartDate = request.getParameter(AttributesContainer.START_DATE.toString());
        String sEndDate = request.getParameter(AttributesContainer.END_DATE.toString());
        Date startDate = new Date();
        try {
            startDate = new SimpleDateFormat(ValuesContainer.SIMPLE_DATE_FORMAT).parse(sStartDate);
        } catch (ParseException e) {
            LOGGER.warn("Start date parsing failed.", e);
        }
        Date endDate = new Date();
        try {
            endDate = new SimpleDateFormat(ValuesContainer.SIMPLE_DATE_FORMAT).parse(sEndDate);
        } catch (ParseException e) {
            LOGGER.warn("End date parsing failed.", e);
        }
        String sGamesType = request.getParameter(AttributesContainer.GAME_TYPE.toString());
        Game.GameType commonGameType = Game.GameType.fromString(sGamesType).orElse(ValuesContainer.DEFAULT_GAME_TYPE);

        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());

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
