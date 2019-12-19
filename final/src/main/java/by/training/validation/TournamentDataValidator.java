package by.training.validation;

import by.training.core.ApplicationContext;
import by.training.core.ServiceException;
import by.training.resourse.AppSetting;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import by.training.tournament.TournamentService;
import by.training.tournament.TournamentValidationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TournamentDataValidator implements InputDataValidator<TournamentValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(TournamentDataValidator.class);
    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private TournamentService tournamentService;
    private final LocalizationManager localizationManager;


    public TournamentDataValidator(TournamentService tournamentService, LocalizationManager localizationManager) {
        this.tournamentService = tournamentService;
        this.localizationManager = localizationManager;
    }


    public ValidationResult validate(TournamentValidationDto dto) throws ValidationException {
        ValidationResult logoSize = logoSize(dto.getLogoSize());
        if (!logoSize.isValid()) {
            return logoSize;
        }

        ValidationResult nameCorrectness = nameCorrectness(dto.getName());
        if (!nameCorrectness.isValid()) {
            return nameCorrectness;
        }

        ValidationResult nameUniqueness = nameUniqueness(dto.getName());
        if (!nameUniqueness.isValid()) {
            return nameUniqueness;
        }

        ValidationResult reward = rewardRate(dto.getOrganizerRewardPercentage());
        if (!reward.isValid()) {
            return reward;
        }

        ValidationResult bonus = bonus(dto.getFromOrganizerBonus());
        if (!bonus.isValid()) {
            return bonus;
        }

        ValidationResult buyIn = buyIn(dto.getBuyIn());
        if (!buyIn.isValid()) {
            return buyIn;
        }

        ValidationResult playersNumber = playersNumber(dto.getPlayersNumber());
        if (!playersNumber.isValid()) {
            return playersNumber;
        }

        return new ValidationResult();
    }


    public ValidationResult logoSize(long size) {
        ValidationResult result = new ValidationResult();

        String sAllowedSize = setting.get(AppSetting.SettingName.IMAGE_ALLOWED_SIZE);
        if (size > Long.parseLong(sAllowedSize)) {
            result.addIfAbsent(AttributesContainer.IMAGE_SIZE_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.IMAGE_SIZE_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult nameCorrectness(String name) {
        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.TOURNAMENT_NAME_REGEXP);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.find()) {
            result.add(AttributesContainer.TOURNAMENT_NAME_CORRECTNESS_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.TOURNAMENT_NAME_CORRECTNESS_ERROR.toString()));
        }
        return result;
    }


    public ValidationResult nameUniqueness(String name) throws ValidationException {
        ValidationResult result = new ValidationResult();

        try {
            if (tournamentService.findByName(name) != null) {
                result.addIfAbsent(AttributesContainer.TOURNAMENT_NAME_UNIQUENESS_ERROR.toString(),
                        localizationManager.getValue(AttributesContainer.TOURNAMENT_NAME_UNIQUENESS_ERROR.toString()));
            }

        } catch (ServiceException e) {
            LOGGER.error("Player's name validation failed.", e);
            throw new ValidationException("Player's name validation failed.", e);
        }

        return result;
    }


    public ValidationResult rewardRate(String sReward) {
        ValidationResult result = new ValidationResult();

        try {
            double reward = Double.parseDouble(sReward);
            if (reward < 0 || reward > 100) {
                LOGGER.error(sReward + " is not valid double value.");
                throw new NumberFormatException(sReward + " is not valid double value.");
            }

        } catch (NumberFormatException e) {
            result.add(AttributesContainer.REWARD_CORRECTNESS_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.REWARD_CORRECTNESS_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult bonus(String bonus) {
        ValidationResult result = new ValidationResult();
        try {

            if (Double.parseDouble(bonus) < 0) {
                LOGGER.error(bonus + " is not valid double value.");
                throw new NumberFormatException(bonus + " is not valid double value.");
            }

        } catch (NumberFormatException e) {
            result.add(AttributesContainer.ORGANIZER_BONUS_CORRECTNESS_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.ORGANIZER_BONUS_CORRECTNESS_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult buyIn(String buyIn) {
        ValidationResult result = new ValidationResult();
        try {
            if (Double.parseDouble(buyIn) < 0) {
                LOGGER.error(buyIn + " is not valid double value.");
                throw new NumberFormatException(buyIn + " is not valid double value.");
            }

        } catch (NumberFormatException e) {
            result.add(AttributesContainer.BUY_IN_CORRECTNESS_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.BUY_IN_CORRECTNESS_ERROR.toString()));
        }

        return result;
    }


    public ValidationResult playersNumber(String playersNumber) {
        ValidationResult result = new ValidationResult();
        try {
            if (Double.parseDouble(playersNumber) < 0) {
                LOGGER.error(playersNumber + " is not valid double value.");
                throw new NumberFormatException(playersNumber + " is not valid double value.");
            }
        } catch (ArithmeticException e) {
            result.add(AttributesContainer.PLAYERS_NUMBER_CORRECTNESS_ERROR.toString(),
                    localizationManager.getValue(AttributesContainer.PLAYERS_NUMBER_CORRECTNESS_ERROR.toString()));
        }

        return result;
    }


}
