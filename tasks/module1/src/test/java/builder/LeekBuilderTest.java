package builder;

import entity.Leek;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import controller.InvalidLineException;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class LeekBuilderTest {

    private Map<String, String> data;
    private LeekBuilder leekBuilder;

    @Before
    public void init() {
        data = new HashMap<>();
        data.put("type", "leek");
        data.put("length", "20");
        data.put("sluggish_leaves_percentage", "16.5");
        data.put("weight", "0.3");
        data.put("calories", "12");

        leekBuilder = new LeekBuilder(data);
    }

    @Test
    public void build() throws InvalidLineException {
        Leek leek = leekBuilder.build();
        Assert.assertEquals(16.5, leek.getSluggishLeavesPercentage(), 0.01);
    }

}
