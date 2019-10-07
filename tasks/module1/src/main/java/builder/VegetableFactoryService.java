package builder;

import controller.InvalidLineException;
import controller.UnableGetTypeException;
import entity.Vegetable;
import entity.VegetableType;
import parser.VegetableParamsParser;

import java.util.Map;
import java.util.Optional;

public class VegetableFactoryService implements EntityCreator<Vegetable> {

    private VegetableParamsParser parser;

    public VegetableFactoryService(VegetableParamsParser parser) {
        this.parser = parser;
    }

    @Override
    public Vegetable createItemFrom(String line) throws InvalidLineException {

        if (line == null) {
            throw new InvalidLineException("Line is null.");
        }

        Map<String, String> vegetableData = parser.getParamsMapFromLine(line);
        String sType = vegetableData.get("type");
        if (sType == null) {
            throw new UnableGetTypeException("Unable to find find \"type\" key.");
        }

        Optional<VegetableType> vegetableType = VegetableType.fromString(sType);
        if (!vegetableType.isPresent()) {
            throw new UnableGetTypeException("Unable to get vegetable type.");
        }

        VegetableType type = vegetableType.get();
        switch (type) {
            case CARROT:
                return new CarrotBuilder(vegetableData).build();
            case CUCUMBER:
                return new CucumberBuilder(vegetableData).build();
            case LEEK:
                return new LeekBuilder(vegetableData).build();
            case TOMATO:
                return new TomatoBuilder(vegetableData).build();
            default:
                throw new UnableGetTypeException("Unexpected exception.");
        }

    }

}
