package by.training.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

@RunWith(JUnit4.class)
public class ActionCommandProviderImplTest {


    @Test
    public void integral() {
        ActionCommand defaultCommand = new DefaultActionCommand();
        ActionCommand changeLanguageToEnCommand = new ChangeLanguageToEnCommand();
        ActionCommandProvider provider = new ActionCommandProviderImpl();

        provider.register(defaultCommand, changeLanguageToEnCommand);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameter("command"))
                .thenReturn(changeLanguageToEnCommand.getType().toString().toLowerCase());

        ActionCommand retrievedCommand =  provider.get(request);

        Assert.assertSame(retrievedCommand.getClass(), ChangeLanguageToEnCommand.class);
    }



}
