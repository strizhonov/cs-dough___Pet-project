package by.training.service;

import by.training.dto.WalletDto;

public interface WalletService {
    long create(WalletDto walletDto) throws ServiceException;
}
