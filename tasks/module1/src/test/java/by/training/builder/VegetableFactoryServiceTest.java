package by.training.builder;

import by.training.controller.ArgumentValueException;
import by.training.controller.InvalidLineException;
import by.training.controller.ParseLineException;
import by.training.controller.UnableGetTypeException;
import by.training.entity.Leek;
import by.training.entity.Vegetable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import by.training.parser.*;

@RunWith(JUnit4.class)
public class VegetableFactoryServiceTest {

    private EntityCreator<Vegetable> entityCreator;

    @Before
    public void init() {
        VegetableParamsParser parser = new VegetableParamsParserImpl();
        entityCreator = new VegetableFactoryService(parser);
    }

    @Test
    public void getVegetableOne() throws InvalidLineException {
        String line = "type:LEEK,length:20,sluggish_leaves_percentage:35,weight:5,calories:3.02";
        Vegetable vegetable = entityCreator.createItemFrom(line);
        Assert.assertTrue(vegetable instanceof Leek);
    }

    @Test(expected = ArgumentValueException.class)
    public void getVegetableTwo() throws InvalidLineException {
        String line = "type:LEEK,length:20,sluggish_leaves_percentage:35,weight:-5,calories:3.02";
        entityCreator.createItemFrom(line);
    }

    @Test(expected = UnableGetTypeException.class)
    public void getVegetableThree() throws InvalidLineException {
        String line = "length:20,sluggish_leaves_percentage:35,weight:5,calories:3.02";
        entityCreator.createItemFrom(line);
    }

    @Test(expected = ArgumentValueException.class)
    public void getVegetableFour() throws InvalidLineException {
        String line = "type:LEEK,length:20,sluggish_leaves_percentage:35,weight:5";
        entityCreator.createItemFrom(line);
    }

    @Test(expected = ParseLineException.class)
    public void getVegetableFive() throws InvalidLineException {
        String line = "";
        entityCreator.createItemFrom(line);
    }

}
