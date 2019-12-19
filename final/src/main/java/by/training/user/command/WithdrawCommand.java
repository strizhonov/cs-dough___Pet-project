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
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response) throws ActionCommandExecutionException {


        UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());
        String sValueToWithdraw = request.getParameter(AttributesContainer.WITHDRAW.toString());
        double valueToWithdraw = Double.parseDouble(sValueToWithdraw);

        try {
            if (userService.withdraw(valueToWithdraw, user)) {

                user = userService.find(user.getId());
                request.getSession().setAttribute(AttributesContainer.USER.toString(), user);

                return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_WALLET_PAGE));


            } else {
                LocalizationManager manager
                        = new LocalizationManager(AttributesContainer.I18N.toString(), request.getLocale());


                request.setAttribute(AttributesContainer.MESSAGE.toString(),
                        manager.getValue(AttributesContainer.NOT_ENOUGH_FUNDS.toString()));

                return Optional.of(new HttpForwarder(PathsContainer.FILE_WALLET_PAGE));

            }


        } catch (ServiceException e) {
            LOGGER.error("Unable to perform withdrawing operation.", e);
            throw new ActionCommandExecutionException("Unable to perform withdrawing operation.", e);
        }

    }

}
