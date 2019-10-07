package service;

import repository.Specification;

import java.util.Comparator;
import java.util.List;

public interface EntityService<T> {

    void addToRepo(T entity);

    List<T> sort(Comparator<T> comparator);

    List<T> findBy(Specification<T> spec);

    List<T> getAll();

}
