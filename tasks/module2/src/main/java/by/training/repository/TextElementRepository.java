package by.training.repository;

import by.training.model.TextLeaf;

import java.util.List;

public interface TextElementRepository<T> {
    long create(T item, long parentId);
    void delete(long id);
    List<T> compile();
    List<TextLeaf> find(ParentIdSpecification spec);
}
