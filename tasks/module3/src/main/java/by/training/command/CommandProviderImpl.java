package by.training.command;

import by.training.entity.Device;

import java.util.HashMap;
import java.util.Map;

public class CommandProviderImpl implements CommandProvider<Device> {

    private Map<CommandType, Command<Device>> commands;

    public CommandProviderImpl() {
        commands = new HashMap<>();
    }

    @Override
    public Command<Device> get(CommandType type) {
        return commands.get(type);
    }

    @Override
    public void register(CommandType type, Command<Device> command) {
        commands.put(type, command);
    }
}
