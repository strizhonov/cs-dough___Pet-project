package by.training.builder;

import by.training.controller.InvalidLineException;
import by.training.entity.Cucumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class CucumberBuilderTest {

    private CucumberBuilder cucumberBuilder;

    @Before
    public void init() {
        Map<String, String> data = new HashMap<>();
        data.put("type", "CUCUMBER");
        data.put("cucumber_type", "SHORTseed");
        data.put("edible_seeds", "true");
        data.put("weight", "0.3");
        data.put("calories", "12");

        cucumberBuilder = new CucumberBuilder(data);
    }

    @Test
    public void build() throws InvalidLineException {
        Cucumber cucumber = cucumberBuilder.build();
        Assert.assertEquals("SHORTSEED", cucumber.getCucumberType().toString());
    }

}
