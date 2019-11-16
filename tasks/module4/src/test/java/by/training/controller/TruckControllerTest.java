package by.training.controller;

import by.training.entity.PlainTruck;
import by.training.entity.PlainWarehouse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import by.training.parser.ToListParser;
import by.training.parser.TruckParser;
import by.training.parser.WarehouseParser;
import by.training.repository.Repository;
import by.training.repository.TruckRepository;
import by.training.repository.WarehouseRepository;
import by.training.service.TruckService;
import by.training.service.WarehouseService;
import by.training.validator.FileValidator;
import by.training.validator.XMLValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class TruckControllerTest {

    private static final int WAREHOUSE_ID = 1;
    private WarehouseService warehouseService;
    private XMLValidator warehouseXMLValidator;
    private FileValidator fileValidator;
    private ToListParser<PlainWarehouse> warehouseParser;
    private TruckController truckController;

    @Before
    public void init() throws URISyntaxException {
        Repository<PlainWarehouse> warehouseRepository = new WarehouseRepository();
        Repository<PlainTruck> truckRepository = new TruckRepository();

        warehouseService = new WarehouseService(warehouseRepository);
        TruckService truckService = new TruckService(truckRepository, warehouseService);

        URI truckXSDUri = this.getClass().getResource("/trucks_xsd_schema.xml").toURI();
        String truckXSDPath = Paths.get(truckXSDUri).toString();
        URI warehouseXSDUri = this.getClass().getResource("/warehouses_xsd_schema.xml").toURI();
        String warehouseXSDPath = Paths.get(warehouseXSDUri).toString();

        XMLValidator truckXMLValidator = new XMLValidator(truckXSDPath);
        warehouseXMLValidator = new XMLValidator(warehouseXSDPath);

        fileValidator = new FileValidator();

        ToListParser<PlainTruck> truckParser = new TruckParser();
        warehouseParser = new WarehouseParser();

        truckController = new TruckController(truckService, fileValidator, truckXMLValidator, truckParser);
    }

    @Test
    public void processValid() throws URISyntaxException, TruckControllerException, WarehouseControllerException {

        URI truckXMLUri = this.getClass().getResource("/valid_trucks.xml").toURI();
        String truckXMLPath = Paths.get(truckXMLUri).toString();
        truckController.processFile(truckXMLPath);

        WarehouseController warehouseController = new WarehouseController(warehouseService, fileValidator,
                warehouseXMLValidator, warehouseParser);

        URI warehouseXMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String warehouseXMLPath = Paths.get(warehouseXMLUri).toString();
        warehouseController.processFile(warehouseXMLPath);

        int size = truckController.getAll().size();
        Assert.assertEquals(13, size);

    }

    @Test
    public void processAnotherValid() throws URISyntaxException, TruckControllerException, WarehouseControllerException {

        URI truckXMLUri = this.getClass().getResource("/another_valid_trucks.xml").toURI();
        String truckXMLPath = Paths.get(truckXMLUri).toString();
        truckController.processFile(truckXMLPath);

        WarehouseController warehouseController = new WarehouseController(warehouseService, fileValidator,
                warehouseXMLValidator, warehouseParser);

        URI warehouseXMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String warehouseXMLPath = Paths.get(warehouseXMLUri).toString();
        warehouseController.processFile(warehouseXMLPath);

        int warehouseCargo = warehouseService.get(WAREHOUSE_ID).getCargoWeight();
        Assert.assertEquals(50, warehouseCargo);

        int size = truckController.getAll().size();
        Assert.assertEquals(15, size);

    }

}
