package by.training.tournament;

import by.training.core.ApplicationContext;
import by.training.resourse.AppSetting;

import java.util.HashMap;
import java.util.Map;


/**
 * Prize rates schema container
 * <p>
 * Contains Map with key == tournament placement, value == reward rate (from whole prize pool) from 0.0 to 1.0
 * <p>
 * Initialized with properties data.
 *
 * @author Uladzislau Stryzhonak\
 */
public class TournamentPrizing {

    private final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
    private final Map<Integer, Double> prizeSchema = new HashMap<>();


    public TournamentPrizing(int playersNumber) {
        initMap(playersNumber);
    }


    private void initMap(int playersNumber) {

        switch (playersNumber) {
            case 2:
                int i = 0;
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.TWO_PLAYERS_FIRST_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.TWO_PLAYERS_SECOND_PRIZE_RATE)));
                break;
            case 4:
                i = 0;
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.FOUR_PLAYERS_FIRST_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.FOUR_PLAYERS_SECOND_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.FOUR_PLAYERS_THIRD_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.FOUR_PLAYERS_FOURTH_PRIZE_RATE)));
                break;
            case 8:
                i = 0;
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_FIRST_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_SECOND_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_THIRD_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_FOURTH_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_FIRST_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_SECOND_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_THIRD_PRIZE_RATE)));
                prizeSchema.put(++i, Double.parseDouble(setting.get(AppSetting.SettingName.EIGHT_PLAYERS_FOURTH_PRIZE_RATE)));
                break;
            default:
                throw new NullPointerException("There is no option for " + playersNumber + " players' tournament.");
        }
    }


    public double getPrizeRate(int placement) {
        return prizeSchema.get(placement);
    }


}