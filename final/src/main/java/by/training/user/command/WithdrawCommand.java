package by.training.user.command;

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
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.validation.GenericDataValidator;
import by.training.validation.InputDataValidator;
import by.training.validation.ValidationException;
import by.training.validation.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class WithdrawCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(WithdrawCommand.class);
    private final ActionCommandType type = ActionCommandType.WITHDRAW;
    private final UserService userService;


    public WithdrawCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        String sValueToWithdraw = request.getParameter(AttributesContainer.WITHDRAW.toString());

        LocalizationManager manager = new LocalizationManager(AttributesContainer.I18N.toString(),
                (String) request.getSession().getAttribute(AttributesContainer.LANGUAGE.toString()));

        InputDataValidator<String> validator = new GenericDataValidator(manager);

        try {

            ValidationResult result = validator.validate(sValueToWithdraw);
            if (!result.isValid()) {
                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(result.getFirstValue()));
                return Optional.of(new HttpForwarder(PathsContainer.FILE_WALLET_PAGE));
            }


            UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());

            if (userService.withdraw(Double.parseDouble(sValueToWithdraw), user)) {

                user = userService.find(user.getId());
                request.getSession().setAttribute(AttributesContainer.USER.toString(), user);


                return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_WALLET_PAGE));


            } else {

                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(AttributesContainer.NOT_ENOUGH_FUNDS.toString()));

                return Optional.of(new HttpForwarder(PathsContainer.FILE_WALLET_PAGE));

            }


        } catch (ServiceException | ValidationException e) {
            LOGGER.error("Unable to perform withdrawing operation.", e);
            throw new ActionCommandExecutionException("Unable to perform withdrawing operation.", e);
        }

    }



}
