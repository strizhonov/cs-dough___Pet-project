package service;

import model.TextLeaf;

public interface CompositeService<T extends TextLeaf> {
    long add(T item, long parentId);
}
