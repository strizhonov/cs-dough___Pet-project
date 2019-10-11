package by.training.repository;

import java.util.List;

public interface Repository<T> {

    void add(T item);
    void remove(T item);

    List<T> findBy(Specification<T> spec);

    List<T> getData();

}
