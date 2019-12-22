package by.training.user;

import by.training.connection.TransactionManager;
import by.training.core.*;
import by.training.security.StringDataCoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.GeneralSecurityException;
import java.util.List;

@Bean
public class UserServiceImpl extends BaseBeanService implements UserService {

    private final static Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final WalletDao walletDao;
    private final StringDataCoder coder;

    public UserServiceImpl(UserDao userDao, WalletDao walletDao, TransactionManager transactionManager)
            throws ServiceException {

        super(transactionManager);
        this.userDao = userDao;
        this.walletDao = walletDao;
        try {
            this.coder = new StringDataCoder();
        } catch (GeneralSecurityException e) {
            LOGGER.error("Unable to create user service.", e);
            throw new ServiceException("Unable to create user service.", e);
        }
    }


    /**
     * Catch Exception for transaction security reasons
     */
    @Override
    public void registerUser(RegistrationDto registrationDto) throws ServiceException {
        try {
            transactionManager.startTransaction();


            String password = registrationDto.getPassword();
            if (password == null) {
                throw new GeneralSecurityException("Password not found.");
            }

            String passKey = coder.getStringSecretKey();
            String encrypted = coder.encrypt(password, passKey);

            UserDto user = new UserDto();

            user.setAvatar(registrationDto.getDefAvatar());
            user.setUsername(registrationDto.getUsername());
            user.setPassword(encrypted);
            user.setPasswordKey(passKey);
            user.setEmail(registrationDto.getEmail());
            user.setLanguage(registrationDto.getLanguage());

            long userId = userDao.save(user);

            WalletDto walletDto = new WalletDto();
            walletDto.setUserId(userId);
            walletDto.setCurrency(registrationDto.getCurrency());

            walletDao.save(walletDto);

            transactionManager.commitTransaction();

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User registering failed.", e);
            throw new ServiceException("User registering failed.", e);
        }
    }


    /**
     * Catch Exception for transaction security reasons
     */
    @Override
    public UserDto login(LoginDto loginDto) throws ServiceException {
        try {
            transactionManager.startTransaction();

            if (loginDto.getPassword() == null) {
                throw new GeneralSecurityException("No password found.");
            }


            UserDto userDto = userDao.findByUsername(loginDto.getUsername());

            if (userDto != null) {

                String encryptedPassword = coder.encrypt(loginDto.getPassword(), userDto.getPasswordKey());

                if (!userDto.getPassword().equals(encryptedPassword)) {
                    userDto = null;
                }
            }

            transactionManager.commitTransaction();

            return userDto;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User login failed.", e);
            throw new ServiceException("User login failed.", e);
        }

    }


    @Override
    public UserDto find(long id) throws ServiceException {
        try {

            return userDao.get(id);

        } catch (EntityNotFoundException e) {
            LOGGER.warn("User not found.", e);
            return null;

        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }


    @Override
    public boolean update(UserDto userDto) throws ServiceException {
        try {

            return userDao.update(userDto);

        } catch (DaoException e) {
            LOGGER.error("User updating failed.", e);
            throw new ServiceException("User updating failed.", e);
        }
    }


    @Override
    public List<UserDto> findAll() throws ServiceException {
        try {

            return userDao.findAll();

        } catch (DaoException e) {
            LOGGER.error("Users retrieving failed.", e);
            throw new ServiceException("Users retrieving failed.", e);
        }
    }


    @Override
    public UserDto findByUsername(String username) throws ServiceException {
        try {

            return userDao.findByUsername(username);

        } catch (DaoException e) {
            LOGGER.error("User retrieving failed.", e);
            throw new ServiceException("User retrieving failed.", e);
        }
    }


    @Override
    public UserDto findByEmail(String email) throws ServiceException {
        try {

            return userDao.findByEmail(email);

        } catch (DaoException e) {
            LOGGER.error("User retrieving failed.", e);
            throw new ServiceException("User retrieving failed.", e);
        }
    }


    /**
     * Catch Exception for transaction security reasons
     */
    @Override
    public void deposit(double value, UserDto user) throws ServiceException {
        try {

            transactionManager.startTransaction();

            WalletDto userWallet = walletDao.getOfUser(user.getId());

            userWallet.increaseBalance(value);
            walletDao.update(userWallet);

            transactionManager.commitTransaction();

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Depositing operation failed.", e);
            throw new ServiceException("Depositing operation failed.", e);
        }

    }


    /**
     * Catch Exception for transaction security reasons
     */
    @Override
    public boolean withdraw(double value, UserDto user) throws ServiceException {
        try {

            transactionManager.startTransaction();

            WalletDto userWallet = walletDao.getOfUser(user.getId());

            if (userWallet.getBalance() < value) {
                transactionManager.rollbackTransaction();
                LOGGER.warn("Not enough funds.");
                return false;
            }

            userWallet.decreaseBalance(value);
            walletDao.update(userWallet);

            transactionManager.commitTransaction();

            return true;

        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Withdrawing operation failed.", e);
            throw new ServiceException("Withdrawing operation failed.", e);
        }
    }


}
