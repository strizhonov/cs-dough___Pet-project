package by.training.user;

import by.training.core.ApplicationContext;
import by.training.core.Entity;
import by.training.resourse.AppSetting;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Wallet extends Entity {

    private static final long serialVersionUID = 4L;

    private double balance;
    private Currency currency = Currency.getDefault();
    private long userId;


    public Wallet() {
    }


    public Wallet(double balance, Currency currency, long userId) {
        this.balance = balance;
        this.currency = currency;
        this.userId = userId;
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

            if (setting == null) {
                return null;
            }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Double.compare(wallet.balance, balance) == 0 &&
                userId == wallet.userId &&
                currency == wallet.currency;
    }


    @Override
    public int hashCode() {
        return Objects.hash(balance, currency, userId);
    }


    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance +
                ", currency=" + currency +
                ", userId=" + userId +
                ", id=" + id +
                '}';
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
