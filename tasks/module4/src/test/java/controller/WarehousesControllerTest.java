package controller;

import entity.PlainWarehouse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import parser.ToListParser;
import parser.WarehouseParser;
import repository.Repository;
import repository.WarehouseRepository;
import service.WarehouseService;
import validator.FileValidator;
import validator.XMLValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class WarehousesControllerTest {

    private WarehouseService warehouseService;
    private XMLValidator warehouseXMLValidator;
    private FileValidator fileValidator;
    private ToListParser<PlainWarehouse> warehouseParser;

    @Before
    public void init() throws URISyntaxException {
        Repository<PlainWarehouse> warehouseRepository = new WarehouseRepository();
        warehouseService = new WarehouseService(warehouseRepository);

        URI warehouseXSDUri = this.getClass().getResource("/warehouses_xsd_schema.xml").toURI();
        String warehouseXSDPath = Paths.get(warehouseXSDUri).toString();

        warehouseXMLValidator = new XMLValidator(warehouseXSDPath);
        fileValidator = new FileValidator();
        warehouseParser = new WarehouseParser();
    }

    @Test
    public void processValid() throws URISyntaxException, WarehouseControllerException {
        WarehouseController warehouseController = new WarehouseController(warehouseService, fileValidator,
                warehouseXMLValidator, warehouseParser);

        URI warehouseXMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String warehouseXMLPath = Paths.get(warehouseXMLUri).toString();
        warehouseController.processFile(warehouseXMLPath);

        int size = warehouseService.getAll().size();
        Assert.assertEquals(1, size);
    }

}
