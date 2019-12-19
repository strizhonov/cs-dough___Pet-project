package by.training.command;

import by.training.core.Bean;
import by.training.resourse.AttributesContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumMap;

@Bean
public class ActionCommandProviderImpl implements ActionCommandProvider {

    private final EnumMap<ActionCommandType, ActionCommand> registered;
    private final ActionCommand defaultCommand;


    public ActionCommandProviderImpl() {
        this.registered = new EnumMap<>(ActionCommandType.class);
        this.defaultCommand = new DefaultActionCommand();
    }


    @Override
    public ActionCommand get(HttpServletRequest request) {
        String param = request.getParameter(AttributesContainer.COMMAND.toString());
        return ActionCommandType.from(param).map(registered::get).orElse(defaultCommand);
    }


    @Override
    public void register(ActionCommand... commands) {
        for (ActionCommand command : commands) {
            registered.put(command.getType(), command);
        }
    }

}
