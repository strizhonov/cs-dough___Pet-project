package by.training.dao;

import java.util.List;

public interface CrudDao<T> {

    long save(T entity) throws DaoException;

    T get(long key) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(long key) throws DaoException;

    List<T> findAll() throws DaoException;
}
