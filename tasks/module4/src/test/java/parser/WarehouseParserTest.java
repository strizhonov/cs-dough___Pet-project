package parser;

import entity.PlainWarehouse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JUnit4.class)
public class WarehouseParserTest {

    @Test
    public void parse() throws URISyntaxException, ParserException {
        URI XMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String XMLPath = Paths.get(XMLUri).toString();

        WarehouseParser parser = new WarehouseParser();
        List<PlainWarehouse> warehouses = parser.parse(XMLPath);

        Assert.assertEquals(1, warehouses.size());
    }

}
