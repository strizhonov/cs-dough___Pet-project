package by.training.command;

import by.training.entity.Device;
import by.training.parser.Parser;
import by.training.parser.ParserException;

import java.util.List;

public class DeviceStAXParserCommand implements Command<Device> {

    private Parser<Device> parser;

    public DeviceStAXParserCommand(Parser<Device> parser) {
        this.parser = parser;
    }

    @Override
    public List<Device> execute(String path) throws CommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            throw new CommandException(e);
        }
    }
}
