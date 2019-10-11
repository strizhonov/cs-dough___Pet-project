package by.training.repository;

import by.training.model.TextLeaf;

public interface TextElementRepository<T extends TextLeaf> {
    long create(T item, long parentId);
}
