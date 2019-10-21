package by.training.parser;

import by.training.entity.Device;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class DeviceStAXParserTest {

    @Test
    public void parse() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        DeviceStAXParser parser = new DeviceStAXParser();
        List<Device> devices = parser.parse(XMLPath);

        Assert.assertEquals(16, devices.size());
    }

    @Test(expected = NullPointerException.class)
    public void parseInvalid() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/invalid_devices.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        DeviceStAXParser parser = new DeviceStAXParser();
        parser.parse(XMLPath);
    }

    @Test(expected = ParserException.class)
    public void parseEmpty() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/empty_file.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        DeviceStAXParser parser = new DeviceStAXParser();
        parser.parse(XMLPath);
    }

}
