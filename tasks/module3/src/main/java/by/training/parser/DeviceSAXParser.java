package by.training.parser;

import by.training.entity.Device;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class DeviceSAXParser implements Parser<Device> {

    private static final Logger LOGGER = Logger.getLogger(DeviceSAXParser.class);

    @Override
    public List<Device> parse(String path) throws ParserException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        DeviceSAXHandler handler = new DeviceSAXHandler();

        try {
            saxParser.parse(path, handler);
        } catch (SAXException | IOException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        return handler.getDevices();
    }
}
