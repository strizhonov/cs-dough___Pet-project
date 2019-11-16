package by.training.service;

import by.training.entity.PlainTruck;
import by.training.entity.PlainWarehouse;
import by.training.model.*;
import org.apache.log4j.Logger;
import by.training.repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TruckService {

    private static final Logger LOGGER = Logger.getLogger(TruckService.class);
    private Repository<PlainTruck> repository;
    private WarehouseService warehouseService;

    public TruckService(Repository<PlainTruck> repository, WarehouseService warehouseService) {
        this.repository = repository;
        this.warehouseService = warehouseService;
    }

    public void processAll(long warehouseId) throws TruckServiceException {
        try {
            Warehouse warehouse = warehouseService.getWarehouse(warehouseId);
            List<Truck> trucks = new ArrayList<>();
            for (PlainTruck plainTruck : getAll()) {
                Truck truck = getTruck(plainTruck, warehouse);
                trucks.add(truck);
            }

            Comparator<Truck> defComparator = new TruckDefaultComparator();
            TruckQueueManager queueManager = new TruckQueueManager(this, trucks, warehouse, defComparator);
            queueManager.processQueue();

            PlainWarehouse plainWarehouse = warehouseService.getPlainWarehouse(warehouse);
            plainWarehouse.setId(warehouseId);
            warehouseService.update(plainWarehouse);

        } catch (WarehouseServiceException | TruckQueueManagerException e) {
            LOGGER.error(e);
            throw new TruckServiceException(e);
        }
    }

    public PlainTruck getPlainTruck(Truck truck) {
        long id = truck.getId();
        int capacity = truck.getCapacity();
        int cargoWeight = truck.getCargoWeight();
        boolean perishable = truck.isPerishable();

        return new PlainTruck(id, capacity, cargoWeight, perishable);
    }

    public Truck getTruck(PlainTruck plainTruck, Warehouse warehouse) {
        long id = plainTruck.getId();
        int capacity = plainTruck.getCapacity();
        int cargoWeight = plainTruck.getCargoWeight();
        boolean perishable = plainTruck.isPerishable();

        return new Truck(id, capacity, cargoWeight, perishable, warehouse);
    }

    public void create(PlainTruck item) {
        repository.create(item);
    }

    public PlainTruck get(long id) {
        return repository.get(id);
    }

    public void update(PlainTruck item) {
        repository.update(item);
    }

    public void delete(long id) {
        repository.delete(id);
    }

    public List<PlainTruck> getAll() {
        return repository.getAll();
    }

}
