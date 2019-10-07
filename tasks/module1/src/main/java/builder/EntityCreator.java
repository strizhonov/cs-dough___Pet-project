package builder;

import controller.InvalidLineException;

public interface EntityCreator<T> {

    T createItemFrom(String line) throws InvalidLineException;

}
