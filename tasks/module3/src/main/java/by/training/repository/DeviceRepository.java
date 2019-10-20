package by.training.repository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository<T> {
    void create(T item);

    Optional<T> get(String id);

    void update(T item);

    void delete(String id);

    List<T> getAll();
}
