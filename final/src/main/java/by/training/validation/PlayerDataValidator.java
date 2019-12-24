package by.training.validation;

import by.training.core.ServiceException;
import by.training.player.PlayerDto;
import by.training.player.PlayerService;
import by.training.player.PlayerValidationDto;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDataValidator extends ImageValidator implements InputDataValidator<PlayerValidationDto>,
        UpdatedDataValidator<PlayerDto, PlayerValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(PlayerDataValidator.class);
    private final PlayerService playerService;


    public PlayerDataValidator(PlayerService playerService, LocalizationManager localizationManager) {
        super(localizationManager);
        this.playerService = playerService;
    }


    @Override
    public ValidationResult validate(PlayerValidationDto dto) throws ValidationException {
        ValidationResult result = imageSize(dto.getPhotoSize());
        if (!result.isValid()) {
            return result;
        }

        result = imageType(dto.getPhotoType());
        if (!result.isValid()) {
            return result;
        }

        result = nicknameCorrectness(dto.getNickname());
        if (!result.isValid()) {
            return result;
        }

        result = nicknameUniqueness(dto.getNickname());
        if (!result.isValid()) {
            return result;
        }

        result = nameCorrectness(dto.getName());
        if (!result.isValid()) {
            return result;
        }

        result = surnameCorrectness(dto.getSurname());

        return result;

    }


    @Override
    public ValidationResult validate(PlayerDto previous, PlayerValidationDto updated) throws ValidationException {
        ValidationResult result = new ValidationResult();
        if (updated.getPhoto() != null) {

            result = imageSize(updated.getPhotoSize());
            if (!result.isValid()) {
                return result;
            }

            result = imageType(updated.getPhotoType());
            if (!result.isValid()) {
                return result;
            }

        }


        if (!previous.getNickname().equals(updated.getNickname())) {

            result = nicknameCorrectness(updated.getNickname());
            if (!result.isValid()) {
                return result;
            }

            result = nicknameUniqueness(updated.getNickname());
            if (!result.isValid()) {
                return result;
            }
        }


        if (!previous.getName().equals(updated.getName())) {

            result = nameCorrectness(updated.getName());
            if (!result.isValid()) {
                return result;
            }

        }


        if (!previous.getSurname().equals(updated.getSurname())) {
            result = surnameCorrectness(updated.getSurname());
        }

        return result;
    }


    public ValidationResult nicknameCorrectness(String nickname) throws ValidationException {
        if (nickname == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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
        if (nickname == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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


    public ValidationResult nameCorrectness(String name) throws ValidationException {
        if (name == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.PLAYER_NAME_REGEXP);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_PLAYER_NAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_PLAYER_NAME.toString()));
        }
        return result;
    }


    public ValidationResult surnameCorrectness(String surname) throws ValidationException {
        if (surname == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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
