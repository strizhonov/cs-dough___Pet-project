package by.training.parser;

import by.training.entity.*;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static by.training.parser.ParserConstantsContainer.*;

public class DeviceDOMParser implements Parser<Device> {
    private static final int NAME_INDEX = 1;
    private static final int ORIGIN_INDEX = 3;
    private static final int PRICE_INDEX = 5;
    private static final int PARAMETERS_INDEX = 7;
    private static final int GROUP_INDEX = 1;
    private static final int PORT_INDEX = 3;
    private static final Logger LOGGER = Logger.getLogger(DeviceDOMParser.class);
    private Device.Builder deviceBuilder;

    public List<Device> parse(String path) throws ParserException {
        List<Device> deviceList = new LinkedList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        Document document;
        try {
            document = builder.parse(path);
        } catch (SAXException | IOException e) {
            LOGGER.error(e);
            throw new ParserException(e);
        }

        NodeList devices = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < devices.getLength(); i++) {
            Node node = devices.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Device device = buildDeviceFromNode(node);
            deviceList.add(device);
        }

        return deviceList;
    }

    private Device buildDeviceFromNode(Node node) throws ParserException {
        String id = node.getAttributes().getNamedItem(ID).getNodeValue();
        boolean critical;
        Node criticalAttr = node.getAttributes().getNamedItem(CRITICAL);
        if (criticalAttr == null) {
            critical = false;
        } else {
            String sCritical = criticalAttr.getNodeValue();
            critical = Boolean.parseBoolean(sCritical);
        }

        NodeList nodes = node.getChildNodes();
        String name = nodes.item(NAME_INDEX).getTextContent();
        String origin = nodes.item(ORIGIN_INDEX).getTextContent();
        String sPrice = nodes.item(PRICE_INDEX).getTextContent();
        int price = Integer.parseInt(sPrice);

        Node childNode = nodes.item(PARAMETERS_INDEX);
        DeviceAttributesContainer attributesContainer = buildDeviceAttributeContainer(childNode);

        String sDeviceType = ((Element) node).getTagName();
        Optional<DeviceType> optType = DeviceType.fromString(sDeviceType);
        if (!optType.isPresent()) {
            LOGGER.error("Device type " + sDeviceType + " not found.");
            throw new ParserException("Device type " + sDeviceType + " not found.");
        }
        DeviceType deviceType = optType.get();

        switch (deviceType) {
            case PROCESSOR:
                deviceBuilder = new Processor.Builder().setId(id)
                        .setCritical(critical)
                        .setName(name)
                        .setOrigin(origin)
                        .setPrice(price)
                        .setAttributesContainer(attributesContainer);
                return buildProcessor(node);

            case MOTHERBOARD:
                deviceBuilder = new MotherBoard.Builder().setId(id)
                        .setCritical(critical)
                        .setName(name)
                        .setOrigin(origin)
                        .setPrice(price)
                        .setAttributesContainer(attributesContainer);
                return buildMotherBoard(node);

            case MOUSE:
                deviceBuilder = new Mouse.Builder().setId(id)
                        .setCritical(critical)
                        .setName(name)
                        .setOrigin(origin)
                        .setPrice(price)
                        .setAttributesContainer(attributesContainer);
                return buildMouse(node);

            default:
                throw new ParserException();
        }

    }

    private Mouse buildMouse(Node node) {
        String sButtons = node.getAttributes().getNamedItem(BUTTONS).getNodeValue();
        int buttons = Integer.parseInt(sButtons);

        return ((Mouse.Builder) deviceBuilder).setButtons(buttons).build();
    }

    private Processor buildProcessor(Node node) {
        String sConsumption = node.getAttributes().getNamedItem(CONSUMPTION).getNodeValue();
        int consumption = Integer.parseInt(sConsumption);

        return ((Processor.Builder) deviceBuilder).setConsumption(consumption).build();
    }

    private MotherBoard buildMotherBoard(Node node) {
        String sRamSlots = node.getAttributes().getNamedItem(RAM).getNodeValue();
        int ramSlots = Integer.parseInt(sRamSlots);

        return ((MotherBoard.Builder) deviceBuilder).setRamSlots(ramSlots).build();
    }

    private DeviceAttributesContainer buildDeviceAttributeContainer(Node node) throws ParserException {
        String sPeripheral = node.getAttributes().getNamedItem(PERIPHERAL).getNodeValue();
        boolean peripheral = Boolean.parseBoolean(sPeripheral);
        String sCooler = node.getAttributes().getNamedItem(COOLER).getNodeValue();
        boolean cooler = Boolean.parseBoolean(sCooler);

        NodeList childNodes = node.getChildNodes();
        String group = childNodes.item(GROUP_INDEX).getTextContent();
        String sPortType = childNodes.item(PORT_INDEX).getTextContent();
        Optional<PortType> optPort = PortType.fromString(sPortType);
        if (!optPort.isPresent()) {
            LOGGER.error("PortType " + sPortType + " not found.");
            throw new ParserException("PortType " + sPortType + " not found.");
        }
        PortType portType = optPort.get();

        return new DeviceAttributesContainer.Builder()
                .setPeripheral(peripheral)
                .setCooler(cooler)
                .setGroup(group)
                .setPortType(portType)
                .build();
    }

}
