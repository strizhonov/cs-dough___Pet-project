package parser;

import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public abstract class StAXToListParser<T> implements ToListParser<T> {

    private static final Logger LOGGER = Logger.getLogger(StAXToListParser.class);
    protected String data;
    protected List<T> items;

    @Override
    public List<T> parse(String path) throws ParserException {
        items = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();

        try {
            FileReader reader = new FileReader(path);
            XMLEventReader eventReader = factory.createXMLEventReader(reader);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                proceedEvent(event);
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        return items;
    }

    private void proceedEvent(XMLEvent event) throws ParserException {
        switch (event.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                proceedStartElement(event);
                break;
            case XMLStreamConstants.CHARACTERS:
                Characters characters = event.asCharacters();
                data = characters.getData();
                break;
            case XMLStreamConstants.END_ELEMENT:
                proceedEndElement(event);
                break;
            default:
                break;
        }
    }

    protected abstract void proceedStartElement(XMLEvent event) throws ParserException;

    protected abstract void proceedEndElement(XMLEvent event) throws ParserException;

}
