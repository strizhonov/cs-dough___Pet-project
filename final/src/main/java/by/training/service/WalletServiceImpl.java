package by.training.service;

import by.training.connection.TransactionManager;
import by.training.dao.WalletDao;
import by.training.dto.WalletDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WalletServiceImpl extends BaseBeanService implements WalletService {

    private static Logger LOGGER = LogManager.getLogger(WalletServiceImpl.class);
    private WalletDao walletDao;

    public WalletServiceImpl(TransactionManager transactionManager, WalletDao walletDao) {
        super(transactionManager);
        this.walletDao = walletDao;
    }

    @Override
    public long create(WalletDto walletDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long id = walletDao.save(walletDto);
            transactionManager.commitTransaction();
            return id;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Unable to create wallet.", e);
            throw new ServiceException("Unable to create wallet.", e);
        }
    }
}
