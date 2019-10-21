package by.training.parser;

import by.training.entity.Device;
import by.training.entity.DeviceProperties;
import by.training.entity.DeviceType;
import by.training.entity.MotherBoard;
import by.training.entity.Mouse;
import by.training.entity.PortType;
import by.training.entity.Processor;
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

import static by.training.parser.ParserConstantsContainer.BUTTONS;
import static by.training.parser.ParserConstantsContainer.CONSUMPTION;
import static by.training.parser.ParserConstantsContainer.COOLER;
import static by.training.parser.ParserConstantsContainer.CRITICAL;
import static by.training.parser.ParserConstantsContainer.GROUP;
import static by.training.parser.ParserConstantsContainer.ID;
import static by.training.parser.ParserConstantsContainer.NAME;
import static by.training.parser.ParserConstantsContainer.ORIGIN;
import static by.training.parser.ParserConstantsContainer.PERIPHERAL;
import static by.training.parser.ParserConstantsContainer.PORT;
import static by.training.parser.ParserConstantsContainer.PRICE;
import static by.training.parser.ParserConstantsContainer.RAM;
import static by.training.parser.ParserConstantsContainer.TYPES;

public class DeviceDOMParser implements Parser<Device> {
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

        StringBuilder name = new StringBuilder();
        StringBuilder origin = new StringBuilder();
        StringBuilder sPrice = new StringBuilder();
        int price = 0;
        DeviceProperties attributesContainer = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node childNode = nodes.item(i);
            String nodeName = childNode.getNodeName();
            switch (nodeName) {
                case NAME:
                    String content = childNode.getTextContent();
                    name.append(content);
                    break;
                case ORIGIN:
                    content = childNode.getTextContent();
                    origin.append(content);
                    break;
                case PRICE:
                    content = childNode.getTextContent();
                    sPrice.append(content);
                    price = Integer.parseInt(sPrice.toString());
                    break;
                case TYPES:
                    attributesContainer = buildDeviceAttributeContainer(childNode);
                    break;
                default:
                    break;
            }
        }

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
                        .setName(name.toString())
                        .setOrigin(origin.toString())
                        .setPrice(price)
                        .setAttributesContainer(attributesContainer);
                return buildProcessor(node);

            case MOTHERBOARD:
                deviceBuilder = new MotherBoard.Builder().setId(id)
                        .setCritical(critical)
                        .setName(name.toString())
                        .setOrigin(origin.toString())
                        .setPrice(price)
                        .setAttributesContainer(attributesContainer);
                return buildMotherBoard(node);

            case MOUSE:
                deviceBuilder = new Mouse.Builder().setId(id)
                        .setCritical(critical)
                        .setName(name.toString())
                        .setOrigin(origin.toString())
                        .setPrice(price)
                        .setAttributesContainer(attributesContainer);
                return buildMouse(node);

            default:
                throw new ParserException("There is no parser logic for device type " + sDeviceType + " found.");
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

    private DeviceProperties buildDeviceAttributeContainer(Node node) throws ParserException {
        String sPeripheral = node.getAttributes().getNamedItem(PERIPHERAL).getNodeValue();
        boolean peripheral = Boolean.parseBoolean(sPeripheral);
        String sCooler = node.getAttributes().getNamedItem(COOLER).getNodeValue();
        boolean cooler = Boolean.parseBoolean(sCooler);

        NodeList nodes = node.getChildNodes();

        StringBuilder group = new StringBuilder();
        StringBuilder sPortType = new StringBuilder();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node childNode = nodes.item(i);
            String nodeName = childNode.getNodeName();
            switch (nodeName) {
                case GROUP:
                    String content = childNode.getTextContent();
                    group.append(content);
                    break;
                case PORT:
                    content = childNode.getTextContent();
                    sPortType.append(content);
                    break;
                default:
                    break;
            }
        }
        Optional<PortType> optPort = PortType.fromString(sPortType.toString());
        if (!optPort.isPresent()) {
            LOGGER.error("PortType " + sPortType + " not found.");
            throw new ParserException("PortType " + sPortType + " not found.");
        }
        PortType portType = optPort.get();

        return new DeviceProperties.Builder()
                .setPeripheral(peripheral)
                .setCooler(cooler)
                .setGroup(group.toString())
                .setPortType(portType)
                .build();
    }

}
