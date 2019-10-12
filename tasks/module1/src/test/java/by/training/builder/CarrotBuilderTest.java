package by.training.builder;

import by.training.controller.InvalidLineException;
import by.training.entity.Carrot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class CarrotBuilderTest {

    private CarrotBuilder carrotBuilder;

    @Before
    public void init() {
        Map<String, String> data = new HashMap<>();
        data.put("type", "CARROT");
        data.put("has_bitterness", "true");
        data.put("peeled", "true");
        data.put("weight", "0.3");
        data.put("calories", "12");

        carrotBuilder = new CarrotBuilder(data);
    }

    @Test
    public void build() throws InvalidLineException {
        Carrot carrot = carrotBuilder.build();
        Assert.assertEquals(12, carrot.getCalories(), 0.01);
    }

}
