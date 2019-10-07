package parser;

import controller.InvalidLineException;
import controller.ParseLineException;

import java.util.HashMap;
import java.util.Map;

public class VegetableParamsParserImpl implements VegetableParamsParser {

    @Override
    public Map<String, String> getParamsMapFromLine(String line) throws InvalidLineException {
        String[] params = line.split(",");
        if (params.length == 0) {
            throw new ParseLineException("Parameters not found.");
        }

        Map<String, String> data = new HashMap<>();

        for (String param : params) {
            String[] dataSet = param.split(":");
            if (dataSet.length != 2) {
                throw new ParseLineException("Can't transform parameter to key-value set.");
            }
            data.put(dataSet[0], dataSet[1]);
        }

        return data;
    }

}
