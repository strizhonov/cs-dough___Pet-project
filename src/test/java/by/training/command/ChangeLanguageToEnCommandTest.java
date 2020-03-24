package by.training.command;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RunWith(JUnit4.class)
public class ChangeLanguageToEnCommandTest {


    @Test
    public void direct() throws ActionCommandExecutionException {
        ActionCommand changeLanguageToEn = new ChangeLanguageToEnCommand();

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(request.getSession()).thenReturn(session);

        Assert.assertTrue(changeLanguageToEn.direct(request, null).isPresent());

    }


}
