package by.training.command;

import by.training.appentry.Bean;
import by.training.command.impl.DefaultActionCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumMap;

@Bean
public class ActionCommandProviderImpl implements ActionCommandProvider {

    private EnumMap<ActionCommandType, ActionCommand> registered;
    private ActionCommand defaultCommand;

    public ActionCommandProviderImpl() {
        this.registered = new EnumMap<>(ActionCommandType.class);
        this.defaultCommand = new DefaultActionCommand();
    }

    @Override
    public ActionCommand get(HttpServletRequest request) {
        String param = request.getParameter("command");
        return ActionCommandType.from(param).map(registered::get).orElse(defaultCommand);
    }

    @Override
    public void register(ActionCommand... commands) {
        for (ActionCommand command : commands) {
            registered.put(command.getType(), command);
        }
    }

}
