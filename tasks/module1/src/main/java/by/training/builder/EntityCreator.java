package by.training.builder;

import by.training.controller.InvalidLineException;

public interface EntityCreator<T> {

    T createItemFrom(String line) throws InvalidLineException;

}
