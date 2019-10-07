package builder;

import entity.Tomato;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import controller.InvalidLineException;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class TomatoBuilderTest {

    private Map<String, String> data;
    private TomatoBuilder tomatoBuilder;

    @Before
    public void init() {
        data = new HashMap<>();
        data.put("type", "tomato");
        data.put("sweet", "false");
        data.put("edible_seeds", "true");
        data.put("weight", "0.35");
        data.put("calories", "121");

        tomatoBuilder = new TomatoBuilder(data);
    }

    @Test
    public void build() throws InvalidLineException {
        Tomato tomato = tomatoBuilder.build();
        Assert.assertEquals(121, tomato.getCalories(), 0.01);
    }


}
