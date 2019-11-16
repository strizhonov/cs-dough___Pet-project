package by.training.model;

import by.training.entity.PlainTruck;
import by.training.entity.PlainWarehouse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import by.training.repository.Repository;
import by.training.repository.TruckRepository;
import by.training.repository.WarehouseRepository;
import by.training.service.TruckService;
import by.training.service.WarehouseService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TruckQueueManagerTest {

    private TruckService truckService;

    @Before
    public void init() {
        Repository<PlainWarehouse> warehouseRepository = new WarehouseRepository();
        Repository<PlainTruck> truckRepository = new TruckRepository();

        WarehouseService warehouseService = new WarehouseService(warehouseRepository);
        Comparator<Truck> defComparator = new TruckDefaultComparator();
        truckService = new TruckService(truckRepository, warehouseService, defComparator);
    }

    @Test
    public void processQueue() throws TruckQueueManagerException {
        Comparator<Truck> defComparator = new TruckDefaultComparator();

        Warehouse warehouse = Warehouse.getInstance();
        warehouse.init(100, 33, 3);

        Truck truck1 = new Truck(1, 20, 20, false, warehouse);
        Truck truck2 = new Truck(2, 8, 8, true, warehouse);

        List<Truck> trucks = new ArrayList<>();
        trucks.add(truck1);
        trucks.add(truck2);

        TruckQueueManager manager = new TruckQueueManager(truckService, trucks, warehouse, defComparator);
        manager.processQueue();

        Assert.assertEquals(61, warehouse.getCargoWeight());
    }
}
