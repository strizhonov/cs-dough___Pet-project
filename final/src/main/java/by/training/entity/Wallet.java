package by.training.entity;

import by.training.constant.ValuesContainer;

import java.util.Optional;
import java.util.stream.Stream;

public class Wallet extends Entity {

    private double balance = 0;
    private CurrencyType currency = ValuesContainer.DEFAULT_CURRENCY;

    public enum CurrencyType {
        EUR, USD, BYN;

        public static Optional<CurrencyType> fromString(String type) {
            return Stream.of(CurrencyType.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }
    }

    public Wallet() {
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public static final class Builder {
        protected long id;
        private double balance = 0;
        private CurrencyType currency = ValuesContainer.DEFAULT_CURRENCY;

        private Builder() {
        }

        public static Builder aWallet() {
            return new Builder();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder balance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder currency(CurrencyType currency) {
            this.currency = currency;
            return this;
        }

        public Wallet build() {
            Wallet wallet = new Wallet();
            wallet.setId(id);
            wallet.setBalance(balance);
            wallet.setCurrency(currency);
            return wallet;
        }
    }
}
