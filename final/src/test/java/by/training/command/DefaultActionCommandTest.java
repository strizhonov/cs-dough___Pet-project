package by.training.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

@RunWith(JUnit4.class)
public class DefaultActionCommandTest {


    @Test
    public void direct() throws ActionCommandExecutionException {
        ActionCommand defaultCommand = new DefaultActionCommand();

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Assert.assertTrue(defaultCommand.direct(request, null).isPresent());

    }

}
