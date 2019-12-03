package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.UserDao;
import by.training.dto.UserDto;
import by.training.dto.WalletDto;
import by.training.security.StringDataCoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.GeneralSecurityException;
import java.util.List;

@Bean
public class UserServiceImpl extends BaseBeanService implements UserService {

    private static Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;
    private WalletService walletService;
    private StringDataCoder coder;

    public UserServiceImpl(UserDao userDao, WalletService walletService, TransactionManager transactionManager) throws ServiceException {
        super(transactionManager);
        this.userDao = userDao;
        this.walletService = walletService;
        try {
            this.coder = new StringDataCoder();
        } catch (GeneralSecurityException e) {
            LOGGER.error("Unable to create user service.", e);
            throw new ServiceException("Unable to create user service.", e);
        }
    }

    @Override
    public long registerUser(UserDto userDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            WalletDto walletDto = new WalletDto();
            long walletId = walletService.create(walletDto);
            userDto.setWalletId(walletId);

            String password = userDto.getPassword();
            if (password == null) {
                throw new GeneralSecurityException();
            }
            String passKey = coder.getStringSecretKey();
            String encrypted = coder.encrypt(password, passKey);
            userDto.setPassword(encrypted);
            userDto.setPasswordKey(passKey);

            long id = userDao.save(userDto);
            transactionManager.commitTransaction();
            return id;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User registering failed.", e);
            throw new ServiceException("User registering failed.", e);
        }
    }

    @Override
    public UserDto findBy(String username, String rawPassword) throws ServiceException {
        try {
            transactionManager.startTransaction();
            if (rawPassword == null) {
                throw new GeneralSecurityException("No input password found.");
            }

            UserDto userDto;
            userDto = userDao.findByUsername(username);

            if (userDto == null) {
                return null;
            }

            String encryptedPassword = coder.encrypt(rawPassword, userDto.getPasswordKey());
            if (!userDto.getPassword().equals(encryptedPassword)) {
                userDto = null;
            }
            transactionManager.commitTransaction();
            return userDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User login failed.", e);
            throw new ServiceException("User login failed.", e);
        }

    }

    @Override
    public List<UserDto> getAll() throws ServiceException {
        try {
            transactionManager.startTransaction();
            List<UserDto> result = userDao.findAll();
            transactionManager.commitTransaction();
            return result;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Users retrieving failed.", e);
            throw new ServiceException("Users retrieving failed.", e);
        }
    }

    @Override
    public UserDto find(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            UserDto userDto = userDao.get(id);
            transactionManager.commitTransaction();
            return userDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User retrieving failed.", e);
            throw new ServiceException("User retrieving failed.", e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = userDao.delete(id);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User deleting failed.", e);
            throw new ServiceException("User deleting failed.", e);
        }
    }

    @Override
    public boolean update(UserDto userDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = userDao.update(userDto);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User updating failed.", e);
            throw new ServiceException("User updating failed.", e);
        }
    }

    @Override
    public UserDto findByUsername(String username) throws ServiceException {
        try {
            transactionManager.startTransaction();
            UserDto userDto = userDao.findByUsername(username);
            transactionManager.commitTransaction();
            return userDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User retrieving failed.", e);
            throw new ServiceException("User retrieving failed.", e);
        }
    }

    @Override
    public UserDto findByEmail(String email) throws ServiceException {
        try {
            transactionManager.startTransaction();
            UserDto userDto = userDao.findByEmail(email);
            transactionManager.commitTransaction();
            return userDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User retrieving failed.", e);
            throw new ServiceException("User retrieving failed.", e);
        }
    }
}
