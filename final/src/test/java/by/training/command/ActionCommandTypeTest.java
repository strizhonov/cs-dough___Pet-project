package by.training.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ActionCommandTypeTest {


    @Test
    public void fromString() {
        Assert.assertEquals(ActionCommandType.from("upDate_OrganizeR").orElse(null),
                ActionCommandType.UPDATE_ORGANIZER);

        Assert.assertNull(ActionCommandType.from("upDate_OrganDDWwfizeR").orElse(null));

        Assert.assertEquals(ActionCommandType.from("CREATE_tournament").orElse(null),
                ActionCommandType.CREATE_TOURNAMENT);

        Assert.assertNull(ActionCommandType.from("create-tournament").orElse(null));
    }


}
