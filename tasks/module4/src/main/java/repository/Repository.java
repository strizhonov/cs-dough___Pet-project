package repository;

import java.util.List;

public interface Repository<T> {

    void create(T item);

    T get(long id);

    void update(T item);

    void delete(long id);

    List<T> getAll();

}
