package by.training.command.impl;

import by.training.appentry.ApplicationConstantsContainer;
import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.entity.PlayerDto;
import by.training.entity.UserDto;
import by.training.service.PlayerService;
import by.training.service.ServiceException;
import by.training.servlet.ServletRouter;
import by.training.validation.PlayerDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class CreatePlayerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreatePlayerCommand.class);
    private final ActionCommandType type = ActionCommandType.CREATE_PLAYER;
    private PlayerService playerService;
    private PlayerDataValidator validator;

    public CreatePlayerCommand(PlayerService playerService) {
        this.playerService = playerService;
        this.validator = new PlayerDataValidator(playerService);
    }

    @Override
    public ServletRouter execute(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {
        String name = request.getParameter(ApplicationConstantsContainer.NAME);
        String surname = request.getParameter(ApplicationConstantsContainer.SURNAME);
        String nickname = request.getParameter(ApplicationConstantsContainer.NICKNAME);
        String country = request.getParameter(ApplicationConstantsContainer.COUNTRY);

        if (!isDataValid(nickname, validator, request)) {
            return new ServletRouter(request.getContextPath());
        }

        HttpSession httpSession = request.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute(ApplicationConstantsContainer.USER);
        PlayerDto playerDto = PlayerDto.Builder.aPlayerDto()
                .name(name)
                .surname(surname)
                .nickname(nickname)
                .country(country)
                .userId(userDto.getId())
                .build();

        try {
            playerService.save(playerDto, userDto);
            return new ServletRouter("/app?command=show_player&id=" + userDto.getPlayerAccountId());
        } catch (ServiceException e) {
            LOGGER.error("Unable to perform player creation and user updating", e);
            throw new ActionCommandExecutionException("Unable to perform player creation and user updating", e);
        }

    }

    @Override
    public ActionCommandType getType() {
        return type;
    }


    private boolean isDataValid(String name, PlayerDataValidator validator, HttpServletRequest request)
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

}
