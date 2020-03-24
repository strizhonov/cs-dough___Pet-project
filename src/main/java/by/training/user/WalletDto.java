package by.training.user;

import java.io.Serializable;
import java.util.Objects;

public class WalletDto implements Serializable {

    private static final long serialVersionUID = 4L;

    protected long id;
    protected double balance = 0;
    protected Wallet.Currency currency = Wallet.Currency.getDefault();
    protected long userId;


    public WalletDto() {
    }


    public WalletDto(long id, double balance, Wallet.Currency currency, long userId) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.userId = userId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Wallet.Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Wallet.Currency currency) {
        this.currency = currency;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void increaseBalance(double value) {
        balance += value;
    }

    public void decreaseBalance(double value) {
        balance -= value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletDto walletDto = (WalletDto) o;
        return id == walletDto.id &&
                Double.compare(walletDto.balance, balance) == 0 &&
                userId == walletDto.userId &&
                currency == walletDto.currency;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, balance, currency, userId);
    }


    @Override
    public String toString() {
        return "WalletDto{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency +
                ", userId=" + userId +
                '}';
    }


    public static final class Builder {
        private long id;
        private double balance = 0;
        private Wallet.Currency currency = Wallet.Currency.getDefault();
        private long userId;

        private Builder() {
        }

        public static Builder aWalletDto() {
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

        public Builder currency(Wallet.Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public WalletDto build() {
            WalletDto walletDto = new WalletDto();
            walletDto.setId(id);
            walletDto.setBalance(balance);
            walletDto.setCurrency(currency);
            walletDto.setUserId(userId);
            return walletDto;
        }
    }

}
