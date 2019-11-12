package parser;

import entity.PlainTruck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class TruckParserTest {

    @Test
    public void parse() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/valid_trucks.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        TruckParser parser = new TruckParser();
        List<PlainTruck> trucks = parser.parse(XMLPath);

        Assert.assertEquals(13, trucks.size());
    }

    @Test(expected = NullPointerException.class)
    public void parseInvalid() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/invalid.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        TruckParser parser = new TruckParser();
        parser.parse(XMLPath);
    }

    @Test(expected = ParserException.class)
    public void parseEmpty() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/empty_file.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        TruckParser parser = new TruckParser();
        parser.parse(XMLPath);
    }

}
