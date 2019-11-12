package model;

import entity.PlainTruck;
import entity.PlainWarehouse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import repository.Repository;
import repository.TruckRepository;
import repository.WarehouseRepository;
import service.TruckService;
import service.WarehouseService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)
public class OnlyUnloadingQueueStateTest {

    private TruckService truckService;

    @Before
    public void init() {
        Repository<PlainWarehouse> warehouseRepository = new WarehouseRepository();
        Repository<PlainTruck> truckRepository = new TruckRepository();

        WarehouseService warehouseService = new WarehouseService(warehouseRepository);
        truckService = new TruckService(truckRepository, warehouseService);
    }

    @Test
    public void process() throws TruckQueueManagerStateException {
        TruckQueueManagerState state = new OnlyLoadingQueueState();
        Comparator<Truck> defComparator = new TruckDefaultComparator();

        Warehouse warehouse = Warehouse.getInstance();
        warehouse.init(100, 35, 2);

        Truck truck1 = new Truck(1, 15, 15, false, warehouse);
        Truck truck2 = new Truck(2, 10, 0, true, warehouse);

        List<Truck> trucks = new ArrayList<>();
        trucks.add(truck1);
        trucks.add(truck2);

        TruckQueueManager manager = new TruckQueueManager(truckService, trucks, warehouse, defComparator);
        state.process(manager);

        Assert.assertEquals(40, warehouse.getCargoWeight());
    }

    @Test
    public void processSmallWarehouse() throws TruckQueueManagerStateException {
        TruckQueueManagerState state = new OnlyLoadingQueueState();
        Comparator<Truck> defComparator = new TruckDefaultComparator();

        Warehouse warehouse = Warehouse.getInstance();
        warehouse.init(10, 10, 2);

        Truck truck1 = new Truck(1, 10, 0, false, warehouse);
        Truck truck2 = new Truck(2, 8, 8, true, warehouse);

        List<Truck> trucks = new ArrayList<>();
        trucks.add(truck1);
        trucks.add(truck2);

        TruckQueueManager manager = new TruckQueueManager(truckService, trucks, warehouse, defComparator);
        state.process(manager);

        Assert.assertEquals(8, warehouse.getCargoWeight());
    }
}
