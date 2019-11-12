package controller;

import entity.PlainTruck;
import entity.PlainWarehouse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import parser.ToListParser;
import parser.TruckParser;
import parser.WarehouseParser;
import repository.Repository;
import repository.TruckRepository;
import repository.WarehouseRepository;
import service.TruckService;
import service.WarehouseService;
import validator.FileValidator;
import validator.XMLValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class IntegrationTest {

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

        int warehouseCargo = warehouseService.get(WAREHOUSE_ID).getCargoWeight();
        Assert.assertEquals(50, warehouseCargo);

        truckController.processAll(WAREHOUSE_ID);
        warehouseCargo = warehouseService.get(WAREHOUSE_ID).getCargoWeight();
        Assert.assertEquals(103, warehouseCargo);
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

        truckController.processAll(WAREHOUSE_ID);
        warehouseCargo = warehouseService.get(WAREHOUSE_ID).getCargoWeight();
        Assert.assertEquals(25, warehouseCargo);

    }

    @Test(expected = TruckControllerException.class)
    public void processTrucksWithoutCargo() throws URISyntaxException, TruckControllerException, WarehouseControllerException {

        URI truckXMLUri = this.getClass().getResource("/only_empty_trucks.xml").toURI();
        String truckXMLPath = Paths.get(truckXMLUri).toString();
        truckController.processFile(truckXMLPath);

        WarehouseController warehouseController = new WarehouseController(warehouseService, fileValidator,
                warehouseXMLValidator, warehouseParser);

        URI warehouseXMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String warehouseXMLPath = Paths.get(warehouseXMLUri).toString();
        warehouseController.processFile(warehouseXMLPath);

        truckController.processAll(WAREHOUSE_ID);

    }

    @Test(expected = TruckControllerException.class)
    public void processFullTrucks() throws URISyntaxException, TruckControllerException, WarehouseControllerException {

        URI truckXMLUri = this.getClass().getResource("/only_full_trucks.xml").toURI();
        String truckXMLPath = Paths.get(truckXMLUri).toString();
        truckController.processFile(truckXMLPath);

        WarehouseController warehouseController = new WarehouseController(warehouseService, fileValidator,
                warehouseXMLValidator, warehouseParser);

        URI warehouseXMLUri = this.getClass().getResource("/warehouses.xml").toURI();
        String warehouseXMLPath = Paths.get(warehouseXMLUri).toString();
        warehouseController.processFile(warehouseXMLPath);

        truckController.processAll(WAREHOUSE_ID);
    }

}
