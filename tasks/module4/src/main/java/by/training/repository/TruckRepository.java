package by.training.repository;

import by.training.entity.PlainTruck;

import java.util.ArrayList;
import java.util.List;

public class TruckRepository implements Repository<PlainTruck> {

    private List<PlainTruck> trucks;

    public TruckRepository() {
        trucks = new ArrayList<>();
    }

    @Override
    public List<PlainTruck> getAll() {
        return trucks;
    }

    @Override
    public void create(PlainTruck item) {
        this.trucks.add(item);
    }

    @Override
    public PlainTruck get(long id) {
        return this.trucks.stream()
                .filter((item) -> item.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public void update(PlainTruck item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (PlainTruck truck : trucks) {
            if (truck.getId() == id) {
                trucks.remove(truck);
                break;
            }
        }
    }

}
