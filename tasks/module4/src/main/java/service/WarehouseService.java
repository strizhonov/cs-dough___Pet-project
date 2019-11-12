package service;

import entity.PlainWarehouse;
import model.Warehouse;
import org.apache.log4j.Logger;
import repository.Repository;

import java.util.List;

public class WarehouseService {

    private static final Logger LOGGER = Logger.getLogger(WarehouseService.class);
    private static final int DEF_PLAIN_WAREHOUSE_ID = -1;
    private Repository<PlainWarehouse> repository;

    public WarehouseService(Repository<PlainWarehouse> repository) {
        this.repository = repository;
    }

    public Warehouse getWarehouse(long warehouseId) throws WarehouseServiceException {
        PlainWarehouse plainWarehouse = get(warehouseId);
        if (plainWarehouse == null) {
            LOGGER.error("PlainWarehouse by " + warehouseId + " has not found.");
            throw new WarehouseServiceException("PlainWarehouse by " + warehouseId + " has not found.");
        }

        int capacity = plainWarehouse.getCapacity();
        int cargoWeight = plainWarehouse.getCargoWeight();
        int terminalsCount = plainWarehouse.getTerminalsCount();

        Warehouse warehouse = Warehouse.getInstance();
        warehouse.init(capacity, cargoWeight, terminalsCount);

        return warehouse;
    }

    public PlainWarehouse getPlainWarehouse(Warehouse warehouse) {
        int capacity = warehouse.getCapacity();
        int cargoWeight = warehouse.getCargoWeight();
        int terminalsCount = warehouse.getTerminalsCount();

        return new PlainWarehouse(DEF_PLAIN_WAREHOUSE_ID, capacity, cargoWeight, terminalsCount);
    }

    public void create(PlainWarehouse item) {
        repository.create(item);
    }

    public PlainWarehouse get(long id) {
        return repository.get(id);
    }

    public void update(PlainWarehouse item) {
        repository.update(item);
    }

    public void delete(long id) {
        repository.delete(id);
    }

    public List<PlainWarehouse> getAll() {
        return repository.getAll();
    }

}
