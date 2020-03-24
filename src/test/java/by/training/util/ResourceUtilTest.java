package by.training.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ResourceUtilTest {

    @Test
    public void fromConstantToProperty() {

        String toProcess = "_____fsdhf____sjee";

        String processed = ResourceUtil.fromConstantToProperty(toProcess);

        Assert.assertFalse(processed.contains("_"));
        Assert.assertTrue(processed.contains("."));
    }

}
