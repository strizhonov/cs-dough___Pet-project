package by.training.controller;

import by.training.command.CommandProvider;
import by.training.command.CommandProviderImpl;
import by.training.command.CommandType;
import by.training.command.DeviceDOMParserCommand;
import by.training.command.DeviceSAXParserCommand;
import by.training.command.DeviceStAXParserCommand;
import by.training.entity.Device;
import by.training.parser.DeviceDOMParser;
import by.training.parser.DeviceSAXParser;
import by.training.parser.DeviceStAXParser;
import by.training.repository.DeviceRepositoryImpl;
import by.training.service.DeviceService;
import by.training.validator.FileValidator;
import by.training.validator.XMLValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class DeviceControllerTest {

    private DeviceController controller;
    private DeviceRepositoryImpl repository;
    private CommandProvider<Device> provider;

    @Before
    public void init() throws URISyntaxException {
        FileValidator fileValidator = new FileValidator();

        URI XSDUri = this.getClass().getResource("/devices_xsd_schema.xml").toURI();
        String XSDPath = Paths.get(XSDUri).toString();
        XMLValidator xmlValidator = new XMLValidator(XSDPath);

        provider = new CommandProviderImpl();
        repository = new DeviceRepositoryImpl();
        DeviceService service = new DeviceService(repository);

        controller = new DeviceController(fileValidator, xmlValidator, provider, service);
    }

    @Test
    public void processDOM() throws URISyntaxException, DeviceControllerException {
        DeviceDOMParser domParser = new DeviceDOMParser();
        DeviceDOMParserCommand domCommand = new DeviceDOMParserCommand(domParser);
        provider.register(CommandType.DOM, domCommand);

        URI XMLUri = this.getClass().getResource("/devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        controller.process(XMLPath, CommandType.DOM);
        List<Device> devices = repository.getAll();
        Assert.assertEquals(16, devices.size());
    }

    @Test
    public void processSAX() throws URISyntaxException, DeviceControllerException {
        DeviceSAXParser saxParser = new DeviceSAXParser();
        DeviceSAXParserCommand saxCommand = new DeviceSAXParserCommand(saxParser);
        provider.register(CommandType.SAX, saxCommand);

        URI XMLUri = this.getClass().getResource("/devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        controller.process(XMLPath, CommandType.SAX);
        List<Device> devices = repository.getAll();
        Assert.assertEquals(16, devices.size());
    }

    @Test
    public void processStAX() throws URISyntaxException, DeviceControllerException {
        DeviceStAXParser staxParser = new DeviceStAXParser();
        DeviceStAXParserCommand staxCommand = new DeviceStAXParserCommand(staxParser);
        provider.register(CommandType.STAX, staxCommand);

        URI XMLUri = this.getClass().getResource("/devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        controller.process(XMLPath, CommandType.STAX);
        List<Device> devices = repository.getAll();
        Assert.assertEquals(16, devices.size());
    }

}
