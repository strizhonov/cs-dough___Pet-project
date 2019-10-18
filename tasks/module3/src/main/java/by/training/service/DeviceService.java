package by.training.service;

import by.training.entity.Device;
import by.training.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

public class DeviceService {

    private DeviceRepository<Device> repository;

    public DeviceService(DeviceRepository<Device> repository) {
        this.repository = repository;
    }

    public void create(Device item) {
        repository.create(item);
    }

    public Optional<Device> get(long id) {
        return repository.get(id);
    }

    public void update(Device item) {
        repository.update(item);
    }

    public void delete(long id) {
        repository.delete(id);
    }

    public List<Device> getAll() {
        return repository.getAll();
    }

}
