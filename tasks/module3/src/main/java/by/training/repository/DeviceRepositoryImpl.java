package by.training.repository;

import by.training.entity.Device;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DeviceRepositoryImpl implements DeviceRepository<Device> {

    private List<Device> devices;

    public DeviceRepositoryImpl() {
        this.devices = new LinkedList<>();
    }

    @Override
    public void create(Device item) {
        this.devices.add(item);
    }

    @Override
    public Optional<Device> get(long id) {
        return this.devices.stream().filter((item) -> item.getId() == id).findFirst();
    }

    @Override
    public void update(Device item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (Device device : devices) {
            if (device.getId() == id) {
                devices.remove(device);
                break;
            }
        }
    }

    @Override
    public List<Device> getAll() {
        return this.devices;
    }
}
