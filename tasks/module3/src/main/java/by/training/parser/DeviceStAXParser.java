package by.training.parser;

import by.training.entity.Device;
import by.training.entity.DeviceProperties;
import by.training.entity.MotherBoard;
import by.training.entity.Mouse;
import by.training.entity.PortType;
import by.training.entity.Processor;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.training.parser.ParserConstantsContainer.BUTTONS;
import static by.training.parser.ParserConstantsContainer.CONSUMPTION;
import static by.training.parser.ParserConstantsContainer.COOLER;
import static by.training.parser.ParserConstantsContainer.CRITICAL;
import static by.training.parser.ParserConstantsContainer.DATE;
import static by.training.parser.ParserConstantsContainer.DATE_TIME_FORMAT;
import static by.training.parser.ParserConstantsContainer.GROUP;
import static by.training.parser.ParserConstantsContainer.ID;
import static by.training.parser.ParserConstantsContainer.MOTHERBOARD;
import static by.training.parser.ParserConstantsContainer.MOUSE;
import static by.training.parser.ParserConstantsContainer.NAME;
import static by.training.parser.ParserConstantsContainer.ORIGIN;
import static by.training.parser.ParserConstantsContainer.PERIPHERAL;
import static by.training.parser.ParserConstantsContainer.PORT;
import static by.training.parser.ParserConstantsContainer.PRICE;
import static by.training.parser.ParserConstantsContainer.PROCESSOR;
import static by.training.parser.ParserConstantsContainer.RAM;
import static by.training.parser.ParserConstantsContainer.TYPES;

public class DeviceStAXParser implements Parser<Device> {

    private static final Logger LOGGER = Logger.getLogger(DeviceStAXParser.class);
    private List<Device> deviceList;
    private String data;
    private DeviceProperties.Builder attributesBuilder;
    private Device.Builder deviceBuilder;

    @Override
    public List<Device> parse(String path) throws ParserException {
        deviceList = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();

        FileReader reader;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        XMLEventReader eventReader;
        try {
            eventReader = factory.createXMLEventReader(reader);
        } catch (XMLStreamException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        while (eventReader.hasNext()) {
            XMLEvent event;
            try {
                event = eventReader.nextEvent();
                proceedEvent(event);
            } catch (XMLStreamException e) {
                LOGGER.error(e);
                throw new ParserException(e);
            }
        }

        return deviceList;
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

    private void proceedStartElement(XMLEvent event) {
        StartElement startElement = event.asStartElement();

        String qName = startElement.getName().getLocalPart();
        switch (qName) {
            case PROCESSOR:
                deviceBuilder = new Processor.Builder();
                setDeviceCommonAttributes(startElement);
                setProcessorAttributes(startElement);
                break;
            case MOTHERBOARD:
                deviceBuilder = new MotherBoard.Builder();
                setDeviceCommonAttributes(startElement);
                setMotherboardAttributes(startElement);
                break;
            case MOUSE:
                deviceBuilder = new Mouse.Builder();
                setDeviceCommonAttributes(startElement);
                setMouseAttributes(startElement);
                break;
            case TYPES:
                startDeviceAttributesContainerBuilding(startElement);
                break;
            default:
                break;
        }
    }

    private void setDeviceCommonAttributes(StartElement startElement) {
        QName idName = new QName(ID);
        Attribute idAttribute = startElement.getAttributeByName(idName);
        String id = idAttribute.getValue();

        boolean critical;
        QName criticalName = new QName(CRITICAL);
        Attribute criticalAttribute = startElement.getAttributeByName(criticalName);
        if (criticalAttribute == null) {
            critical = false;
        } else {
            String sCritical = criticalAttribute.getValue();
            critical = Boolean.parseBoolean(sCritical);
        }

        deviceBuilder = deviceBuilder.setId(id).setCritical(critical);
    }

    private void setProcessorAttributes(StartElement startElement) {
        QName consumptionName = new QName(CONSUMPTION);
        Attribute consumptionAttribute = startElement.getAttributeByName(consumptionName);
        String sConsumption = consumptionAttribute.getValue();
        int consumption = Integer.parseInt(sConsumption);

        deviceBuilder = ((Processor.Builder) deviceBuilder).setConsumption(consumption);
    }

    private void setMotherboardAttributes(StartElement startElement) {
        QName ramSlotsName = new QName(RAM);
        Attribute ramSlotsAttribute = startElement.getAttributeByName(ramSlotsName);
        String sRamSlots = ramSlotsAttribute.getValue();
        int ramSlots = Integer.parseInt(sRamSlots);

        deviceBuilder = ((MotherBoard.Builder) deviceBuilder).setRamSlots(ramSlots);
    }

    private void setMouseAttributes(StartElement startElement) {
        QName buttonsName = new QName(BUTTONS);
        Attribute buttonsAttribute = startElement.getAttributeByName(buttonsName);
        String sButtons = buttonsAttribute.getValue();
        int buttons = Integer.parseInt(sButtons);

        deviceBuilder = ((Mouse.Builder) deviceBuilder).setButtons(buttons);
    }

    private void startDeviceAttributesContainerBuilding(StartElement startElement) {
        attributesBuilder = new DeviceProperties.Builder();

        QName peripheralName = new QName(PERIPHERAL);
        Attribute peripheralAttribute = startElement.getAttributeByName(peripheralName);
        String sPeripheral = peripheralAttribute.getValue();
        boolean peripheral = Boolean.parseBoolean(sPeripheral);

        QName coolerName = new QName(COOLER);
        Attribute coolerAttribute = startElement.getAttributeByName(coolerName);
        String sCooler = coolerAttribute.getValue();
        boolean cooler = Boolean.parseBoolean(sCooler);

        attributesBuilder = attributesBuilder.setCooler(cooler).setPeripheral(peripheral);
    }

    private void proceedEndElement(XMLEvent event) throws ParserException {
        EndElement endElement = event.asEndElement();
        String qName = endElement.getName().getLocalPart();
        switch (qName) {
            case PROCESSOR:
            case MOTHERBOARD:
            case MOUSE:
                Device device = deviceBuilder.build();
                deviceList.add(device);
                break;
            case TYPES:
                DeviceProperties attributesContainer = attributesBuilder.build();
                deviceBuilder = deviceBuilder.setAttributesContainer(attributesContainer);
                break;
            case PORT:
                Optional<PortType> optPortType = PortType.fromString(data);
                if (!optPortType.isPresent()) {
                    LOGGER.error("PortType " + data + " not found.");
                    throw new ParserException("PortType " + data + " not found.");
                }
                PortType portType = optPortType.get();
                attributesBuilder.setPortType(portType);
                break;
            case GROUP:
                attributesBuilder.setGroup(data);
                break;
            case PRICE:
                int price = Integer.parseInt(data);
                deviceBuilder = deviceBuilder.setPrice(price);
                break;
            case ORIGIN:
                deviceBuilder = deviceBuilder.setOrigin(data);
                break;
            case NAME:
                deviceBuilder = deviceBuilder.setName(data);
                break;
            case DATE:
                SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
                Date date;
                try {
                    date = format.parse(data);
                } catch (ParseException e) {
                    LOGGER.error(e);
                    throw new ParserException(e);
                }
                deviceBuilder = deviceBuilder.setManufacturingDate(date);
                break;
            default:
                break;
        }
    }


}
