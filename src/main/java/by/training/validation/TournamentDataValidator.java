package by.training.validation;

import by.training.core.ServiceException;
import by.training.resourse.AttributesContainer;
import by.training.resourse.LocalizationManager;
import by.training.resourse.ValidationRegexp;
import by.training.tournament.TournamentService;
import by.training.tournament.TournamentValidationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TournamentDataValidator extends ImageValidator implements InputDataValidator<TournamentValidationDto> {

    private static final Logger LOGGER = LogManager.getLogger(TournamentDataValidator.class);
    private TournamentService tournamentService;

    public TournamentDataValidator(TournamentService tournamentService, LocalizationManager localizationManager) {
        super(localizationManager);
        this.tournamentService = tournamentService;
    }


    public ValidationResult validate(TournamentValidationDto dto) throws ValidationException {
        ValidationResult result = imageSize(dto.getLogoSize());
        if (!result.isValid()) {
            return result;
        }

        result = imageType(dto.getLogoType());
        if (!result.isValid()) {
            return result;
        }

        result = nameCorrectness(dto.getName());
        if (!result.isValid()) {
            return result;
        }

        result = nameUniqueness(dto.getName());
        if (!result.isValid()) {
            return result;
        }

        result = rewardRate(dto.getOrganizerRewardPercentage());
        if (!result.isValid()) {
            return result;
        }

        result = bonus(dto.getFromOrganizerBonus());
        if (!result.isValid()) {
            return result;
        }

        result = buyIn(dto.getBuyIn());
        if (!result.isValid()) {
            return result;
        }

        result = playersNumber(dto.getPlayersNumber());

        return result;
    }


    public ValidationResult nameCorrectness(String name) throws ValidationException {
        if (name == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

        ValidationResult result = new ValidationResult();

        Pattern pattern = Pattern.compile(ValidationRegexp.TOURNAMENT_NAME_REGEXP);
        Matcher matcher = pattern.matcher(name);

        if (!matcher.find()) {
            result.add(AttributesContainer.WRONG_TOURNAMENT_NAME.toString(),
                    localizationManager.getValue(AttributesContainer.WRONG_TOURNAMENT_NAME.toString()));
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


    public ValidationResult rewardRate(String sReward) throws ValidationException {
        if (sReward == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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


    public ValidationResult bonus(String bonus) throws ValidationException {
        if (bonus == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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


    public ValidationResult buyIn(String buyIn) throws ValidationException {
        if (buyIn == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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


    public ValidationResult playersNumber(String playersNumber) throws ValidationException {
        if (playersNumber == null) {
            LOGGER.error("Value to validate is null.");
            throw new ValidationException("Value to validate is null.");
        }

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
