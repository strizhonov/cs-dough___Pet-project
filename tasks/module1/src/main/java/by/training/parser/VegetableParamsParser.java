package by.training.parser;

import by.training.controller.InvalidLineException;

import java.util.Map;

public interface VegetableParamsParser {

    Map<String, String> getParamsMapFromLine(String line) throws InvalidLineException;

}
