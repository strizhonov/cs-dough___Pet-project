package repository;

import model.TextLeaf;

public interface TextElementRepository<T extends TextLeaf> {
    long add(T item, long parentId);
}
