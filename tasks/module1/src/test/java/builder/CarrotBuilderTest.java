package builder;

import entity.Carrot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import controller.InvalidLineException;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class CarrotBuilderTest {

    private Map<String, String> data;
    private CarrotBuilder carrotBuilder;

    @Before
    public void init() {
        data = new HashMap<>();
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
