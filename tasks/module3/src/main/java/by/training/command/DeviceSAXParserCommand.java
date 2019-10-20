package by.training.command;

import by.training.entity.Device;
import by.training.parser.Parser;
import by.training.parser.ParserException;
import org.apache.log4j.Logger;

import java.util.List;

public class DeviceSAXParserCommand implements Command<Device> {
    private static final Logger LOGGER = Logger.getLogger(DeviceSAXParserCommand.class);
    private Parser<Device> parser;

    public DeviceSAXParserCommand(Parser<Device> parser) {
        this.parser = parser;
    }

    @Override
    public List<Device> execute(String path) throws CommandException {
        try {
            return parser.parse(path);
        } catch (ParserException e) {
            LOGGER.error(e);
            throw new CommandException(e);
        }
    }


}
