package controller;

import model.TextLeaf;

public interface CompositeController<T extends TextLeaf> {
    long add(T item, long parentId);
    void addAll(T item, long parentId);
}
