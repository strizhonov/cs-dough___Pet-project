package by.training.core;

import java.util.List;

public interface CrudDao<T> {

    long save(T entity) throws DaoException;

    T get(long id) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(long id) throws DaoException;

    List<T> findAll() throws DaoException;

}
