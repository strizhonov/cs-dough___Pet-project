package by.training.validation;

import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.player.PlayerService;
import by.training.player.PlayerValidationDto;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDataValidator implements InputDataValidator<PlayerValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(PlayerDataValidator.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final PlayerService playerService;
    private final LocalizationManager localizationManager;


    public PlayerDataValidator(PlayerService playerService, LocalizationManager localizationManager) {
        this.playerService = playerService;
        this.localizationManager = localizationManager;
    }


    public ValidationResult validate(PlayerValidationDto dto) throws ValidationException {
        ValidationResult photoSize = photoSize(dto.getPhotoSize());
        if (!photoSize.isValid()) {
            return photoSize;
        }

        ValidationResult nicknameCorrectness = nicknameCorrectness(dto.getNickname());
        if (!nicknameCorrectness.isValid()) {
            return nicknameCorrectness;
        }

        ValidationResult nicknameUniqueness = nicknameUniqueness(dto.getNickname());
        if (!nicknameUniqueness.isValid()) {
            return nicknameUniqueness;
        }

        ValidationResult nameCorrectness = nameCorrectness(dto.getName());
        if (!nameCorrectness.isValid()) {
            return nameCorrectness;
        }

        ValidationResult surnameCorrectness = surnameCorrectness(dto.getSurname());
        if (!surnameCorrectness.isValid()) {
            return surnameCorrectness;
        }

        return new ValidationResult();
    }


    public ValidationResult photoSize(long size) {
        ValidationResult result = new ValidationResult();

        String sAllowedSize = setting.get(AppSetting.SettingName.IMAGE_ALLOWED_SIZE);

        if (size > Long.parseLong(sAllowedSize)) {
            result.addIfAbsent(AttributesContainer.IMAGE_SIZE_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.IMAGE_SIZE_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult nicknameCorrectness(String nickname) {
        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.NICKNAME_REGEXP);
        Matcher matcher = pattern.matcher(nickname);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_NICKNAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_NICKNAME.toString()));
        }

        return result;
    }


    public ValidationResult nicknameUniqueness(String nickname) throws ValidationException {
        ValidationResult result = new ValidationResult();
        try {
            if (playerService.findByNickname(nickname) != null) {
                result.addIfAbsent(AttributesContainer.NICKNAME_UNIQUENESS_ERROR.toString(),
                        localizationManager.getValue(AttributesContainer.NICKNAME_UNIQUENESS_ERROR.toString()));
            }
        } catch (ServiceException e) {
            LOGGER.error("Player's name validation failed.", e);
            throw new ValidationException("Player's name validation failed.", e);
        }

        return result;
    }


    public ValidationResult nameCorrectness(String name) {
        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.PLAYER_NAME_REGEXP);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_PLAYER_NAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PLAYER_NAME.toString()));
        }
        return result;
    }


    public ValidationResult surnameCorrectness(String surname) {
        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.PLAYER_SURNAME_REGEXP);
        Matcher matcher = pattern.matcher(surname);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_PLAYER_SURNAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PLAYER_SURNAME.toString()));
        }

        return result;

    }

}
