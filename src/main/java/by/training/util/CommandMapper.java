package by.training.util;

import by.training.core.ApplicationContext;
import by.training.organizer.OrganizerDto;
import by.training.organizer.OrganizerValidationDto;
import by.training.player.PlayerDto;
import by.training.player.PlayerValidationDto;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.PathsContainer;
import by.training.tournament.TournamentValidationDto;
import by.training.user.UserValidationDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CommandMapper {


    private static final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);


    public static OrganizerValidationDto mapOrganizerValidationDto(List<FileItem> items) throws IOException {
        int i = -1;

        FileItem item = items.get(++i);
        long logoSize = item.getSize();

        String type = AttributesContainer.BLANK.toString();
        byte[] logo = null;
        if (logoSize > 0) {
            type = item.getContentType();
            logo = item.get();
        }

        String name = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));


        return new OrganizerValidationDto(logoSize, type, logo, name);
    }


    public static PlayerValidationDto mapPlayerValidationDto(List<FileItem> items) throws IOException {
        int i = -1;

        FileItem item = items.get(++i);
        long photoSize = item.getSize();

        String type = AttributesContainer.BLANK.toString();
        byte[] photo = null;
        if (photoSize > 0) {
            type = item.getContentType();
            photo = item.get();
        }

        String nickname = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String name = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String surname = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        return new PlayerValidationDto(photoSize, type, photo, nickname, name, surname);

    }


    public static TournamentValidationDto mapTournamentValidationDto(List<FileItem> items) throws IOException {
        int i = -1;

        FileItem item = items.get(++i);
        long logoSize = item.getSize();

        String type;
        if (logoSize == 0) {
            type = AttributesContainer.BLANK.toString();
        } else {
            type = item.getContentType();
        }

        String name = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sReward = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sBonus = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sBuyIn = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        String sPlayersNumber = IOUtils.toString(items.get(++i).getInputStream(),
                setting.get(AppSetting.SettingName.STANDARD_CHARSET_NAME));

        return new TournamentValidationDto(logoSize, type, name, sReward, sBonus, sBuyIn, sPlayersNumber);
    }


    public static UserValidationDto mapUserValidationDto(HttpServletRequest request) {

        String username = request.getParameter(AttributesContainer.USERNAME.toString());
        String email = request.getParameter(AttributesContainer.EMAIL.toString());
        String password = request.getParameter(AttributesContainer.PASSWORD.toString());
        String passConfirmation = request.getParameter(AttributesContainer.PASSWORD_CONFIRMATION.toString());

        return new UserValidationDto(username, email, password, passConfirmation);

    }


    public static void merge(OrganizerDto organizer, OrganizerValidationDto validationDto) {
        byte[] logo = validationDto.getLogo();
        if (logo != null) {
            organizer.setLogo(logo);
        }
        organizer.setName(validationDto.getName());
    }


    public static void merge(PlayerDto player, PlayerValidationDto validationDto) {
        byte[] photo = validationDto.getPhoto();
        if (photo != null) {
            player.setPhoto(photo);
        }
        player.setNickname(validationDto.getNickname());
        player.setName(validationDto.getName());
        player.setSurname(validationDto.getSurname());
    }


}
