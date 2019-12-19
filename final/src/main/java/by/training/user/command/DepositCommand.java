package by.training.user.command;

import by.training.command.ActionCommand;
import by.training.command.ActionCommandExecutionException;
import by.training.command.ActionCommandType;
import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.servlet.HttpRedirector;
import by.training.servlet.HttpRouter;
import by.training.user.UserDto;
import by.training.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DepositCommand implements ActionCommand {


    private static final Logger LOGGER = LogManager.getLogger(DepositCommand.class);
    private final ActionCommandType type = ActionCommandType.DEPOSIT;
    private final UserService userService;


    public DepositCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ActionCommandType getType() {
        return type;
    }


    @Override
    public Optional<HttpRouter> direct(HttpServletRequest request, HttpServletResponse response)
            throws ActionCommandExecutionException {

        UserDto user = (UserDto) request.getSession().getAttribute(AttributesContainer.USER.toString());

        String valueToDeposit = request.getParameter(AttributesContainer.DEPOSIT.toString());


        try {
            userService.deposit(Double.parseDouble(valueToDeposit), user);

            return Optional.of(new HttpRedirector(request.getContextPath() + PathsContainer.FILE_WALLET_PAGE));

        } catch (ServiceException e) {
            LOGGER.error("Unable to perform depositing operation.", e);
            throw new ActionCommandExecutionException("Unable to perform depositing operation.", e);
        }

    }

}
