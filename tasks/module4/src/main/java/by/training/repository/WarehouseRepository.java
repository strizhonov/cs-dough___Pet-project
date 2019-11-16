package by.training.repository;

import by.training.entity.PlainWarehouse;

import java.util.ArrayList;
import java.util.List;

public class WarehouseRepository implements Repository<PlainWarehouse> {

    private List<PlainWarehouse> warehouses;

    public WarehouseRepository() {
        warehouses = new ArrayList<>();
    }

    @Override
    public List<PlainWarehouse> getAll() {
        return warehouses;
    }

    @Override
    public void create(PlainWarehouse item) {
        this.warehouses.add(item);
    }

    @Override
    public PlainWarehouse get(long id) {
        return this.warehouses.stream()
                .filter((item) -> item.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public void update(PlainWarehouse item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (PlainWarehouse warehouse : warehouses) {
            if (warehouse.getId() == id) {
                warehouses.remove(warehouse);
                break;
            }
        }
    }
}