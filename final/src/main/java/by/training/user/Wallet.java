package by.training.user;

import by.training.core.AppSetting;
import by.training.common.Entity;
import by.training.core.ApplicationContext;

import java.util.Optional;
import java.util.stream.Stream;

public class Wallet extends Entity {

    private double balance;
    private Currency currency = Currency.getDefault();
    private long userId;

    public Wallet() {
    }

    private Wallet(Builder builder) {
        setId(builder.id);
        setBalance(builder.balance);
        setCurrency(builder.currency);
        setUserId(builder.userId);
    }


    public enum Currency {
        EUR, USD, BYN;

        public static Currency getDefault() {
            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
            return fromString(setting.get(AppSetting.SettingName.DEFAULT_CURRENCY)).orElse(null);
        }

        public static Optional<Currency> fromString(String type) {
            return Stream.of(Currency.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static final class Builder {
        private long id;
        private double balance;
        private Currency currency = Currency.getDefault();
        private long userId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder balance(double val) {
            balance = val;
            return this;
        }

        public Builder currency(Currency val) {
            currency = val;
            return this;
        }

        public Builder userId(long val) {
            userId = val;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }
    }
}
