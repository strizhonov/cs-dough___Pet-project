package by.training.command;

import by.training.game.GameService;
import by.training.resourse.AppSetting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

@RunWith(JUnit4.class)
public class ToHomePageActionCommandException {


    @Test
    public void direct() throws ActionCommandExecutionException, NoSuchFieldException, IllegalAccessException {
        GameService gameService = Mockito.mock(GameService.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        AppSetting setting = Mockito.mock(AppSetting.class);


        ActionCommand toHomePage = new ToHomePageCommand(gameService);
        Field settingField = toHomePage.getClass().getDeclaredField("setting");
        settingField.setAccessible(true);
        settingField.set(toHomePage, setting);

        Mockito.when(setting.get(AppSetting.SettingName.HOME_PAGE_MAX_GAME_RESULTS)).thenReturn("5");

        Assert.assertTrue(toHomePage.direct(request, null).isPresent());

    }


}
