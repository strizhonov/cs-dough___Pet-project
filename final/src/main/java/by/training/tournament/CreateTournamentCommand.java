package by.training.tournament;


import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.constant.AttributesContainer;
import by.training.constant.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.user.UserDto;
import by.training.common.ServiceException;
import by.training.servlet.HttpRouter;
import by.training.servlet.HttpForwarder;
import by.training.validation.TournamentDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

public class CreateTournamentCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateTournamentCommand.class);
    private final ActionCommandType type = ActionCommandType.CREATE_TOURNAMENT;
    private final TournamentService tournamentService;
    private final TournamentDataValidator validator;

    public CreateTournamentCommand(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
        this.validator = new TournamentDataValidator(tournamentService);
    }

    @Override
    public HttpRouter direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            List<FileItem> items = sfu.parseRequest(request);
            String name = items.get(1).getString();
            if (!isDataValid(name, validator, request)) {
                return new HttpForwarder(request.getContextPath());
            }

            TournamentDto tournamentDto = compile(items, request);
            long tournamentId = tournamentService.create(tournamentDto);
            return new HttpForwarder(PathsContainer.CREATE_TOURNAMENT_COMMAND + tournamentId);
        } catch (ServiceException | FileUploadException | IOException e) {
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

    private TournamentDto compile(List<FileItem> items, HttpServletRequest request) throws IOException {
        int i = -1;
        byte[] logo = items.get(++i).get();
        if (logo == null || logo.length == 0) {
            File file = new File(request.getServletContext().getRealPath("/img/blank-logo.jpg"));
            InputStream is = new FileInputStream(file);
            logo = IOUtils.toByteArray(is);
        }
        String name = items.get(++i).getString();
        String sPrizePool = items.get(++i).getString();
        double prizePool = Double.parseDouble(sPrizePool);
        String sPlayersNumber = items.get(++i).getString();
        int playersNumber = Integer.parseInt(sPlayersNumber);
//        String sStartDate = request.getParameter(AttributesContainer.START_DATE.toString());
//        String sEndDate = request.getParameter(AttributesContainer.END_DATE.toString());
//        Date startDate = new Date();
//        try {
//            startDate = new SimpleDateFormat(ValuesContainer.DEFAULT_DATE_FORMAT).parse(sStartDate);
//        } catch (ParseException e) {
//            LOGGER.warn("Start date parsing failed.", e);
//        }
//        Date endDate = new Date();
//        try {
//            endDate = new SimpleDateFormat(ValuesContainer.DEFAULT_DATE_FORMAT).parse(sEndDate);
//        } catch (ParseException e) {
//            LOGGER.warn("End date parsing failed.", e);
//        }

        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(AttributesContainer.USER.toString());

        return new TournamentDto.Builder()
                .name(name)
                .logo(logo)
                .prizePool(prizePool)
                .playersNumber(playersNumber)
//                .startDate(startDate)
//                .endDate(endDate)
                .organizerId(userDto.getOrganizerId())
                .build();
    }

}
