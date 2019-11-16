package by.training.parser;

import java.util.List;

public interface ToListParser<T> {
    List<T> parse(String path) throws ParserException;
}
