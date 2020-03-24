package by.training.core;

import by.training.user.WalletDto;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Supposed to be Application Wallet.
 * General purpose is to store funds of participants
 * between tournament start and final prize distribution.
 *
 * @author Uladzislau Stryzhonak
 */

@Bean
public class BufferWallet extends WalletDto {

    private static final long serialVersionUID = 4L;

    private final AtomicBoolean initialized = new AtomicBoolean();


    private BufferWallet() {

    }

    public static BufferWallet getInstance() {
        return BufferWallet.InstanceHolder.INSTANCE;
    }


    public void init(WalletDto walletDto) {
        if (!initialized.get()) {

            this.id = walletDto.getId();
            this.balance = walletDto.getBalance();
            this.currency = walletDto.getCurrency();
            this.userId = walletDto.getUserId();

            initialized.set(true);
        }
    }


    private Object readResolve() {
        return getInstance();
    }


    private static class InstanceHolder {
        private static final BufferWallet INSTANCE = new BufferWallet();
    }

}
