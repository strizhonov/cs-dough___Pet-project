package by.training.user;

import by.training.common.BaseBeanService;
import by.training.core.Bean;
import by.training.connection.TransactionManager;
import by.training.common.ServiceException;
import by.training.common.DaoException;
import by.training.security.StringDataCoder;
import by.training.tournament.TournamentJoiningDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.GeneralSecurityException;
import java.util.List;

@Bean
public class UserServiceImpl extends BaseBeanService implements UserService {

    private static Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;
    private WalletDao walletDao;
    private PlayerDao playerDao;
    private OrganizerDao organizerDao;
    private StringDataCoder coder;

    public UserServiceImpl(UserDao userDao, WalletDao walletDao, PlayerDao playerDao, OrganizerDao organizerDao,
                           TransactionManager transactionManager) throws ServiceException {
        super(transactionManager);
        this.userDao = userDao;
        this.walletDao = walletDao;
        this.playerDao = playerDao;
        this.organizerDao = organizerDao;
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
            String password = userDto.getPassword();
            if (password == null) {
                throw new GeneralSecurityException();
            }
            String passKey = coder.getStringSecretKey();
            String encrypted = coder.encrypt(password, passKey);
            userDto.setPassword(encrypted);
            userDto.setPasswordKey(passKey);
            long userId = userDao.save(userDto);

            WalletDto walletDto = new WalletDto();
            walletDto.setUserId(userId);
            walletDao.save(walletDto);
            userDto.setBalance(walletDto.getBalance());
            userDto.setCurrency(walletDto.getCurrency());
            transactionManager.commitTransaction();
            return userId;
        } catch (Exception e) {
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
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("User login failed.", e);
            throw new ServiceException("User login failed.", e);
        }

    }

    @Override
    public List<UserDto> getAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Users retrieving failed.", e);
            throw new ServiceException("Users retrieving failed.", e);
        }
    }

    @Override
    public UserDto find(long id) throws ServiceException {
        try {
            return userDao.get(id);
        } catch (DaoException e) {
            LOGGER.error("User retrieving failed.", e);
            throw new ServiceException("User retrieving failed.", e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return userDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("User deleting failed.", e);
            throw new ServiceException("User deleting failed.", e);
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

    @Override
    public PlayerDto findPlayerByNickname(String nickname) throws ServiceException {
        return null;
    }

    @Override
    public long createPlayer(PlayerDto playerDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long playerId = playerDao.save(playerDto);
            UserDto userDto = new UserDto.Builder()
                    .id(playerDto.getUserId())
                    .playerAccountId(playerId)
                    .build();
            userDao.updatePlayerId(userDto);
            transactionManager.commitTransaction();
            return playerId;
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Unable to save player.", e);
            throw new ServiceException("Unable to save player.", e);
        }
    }

    @Override
    public boolean joinTournament(TournamentJoiningDto dto) throws ServiceException {
        return false;
    }


    @Override
    public OrganizerDto findOrganizerByName(String name) throws ServiceException {
        return null;
    }

    @Override
    public long createOrganizer(OrganizerDto organizerDto) throws ServiceException {
        try {
            return organizerDao.save(organizerDto);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public OrganizerDto findOrganizer(long id) throws ServiceException {
        try {
            return organizerDao.get(id);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public PlayerDto findPlayer(long id) throws ServiceException {
        try {
            return playerDao.get(id);
        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }
//
//    @Override
//    public boolean update(PlayerDto playerDto) throws ServiceException {
//        try {
//            return playerDao.update(playerDto);
//        } catch (DaoException e) {
//            LOGGER.error("Unable to update player.", e);
//            throw new ServiceException("Unable to update player.", e);
//        }
//    }
//
//    @Override
//    public boolean delete(long id) throws ServiceException {
//        try {
//            return playerDao.delete(id);
//        } catch (DaoException e) {
//            LOGGER.error("Unable to delete player.", e);
//            throw new ServiceException("Unable to delete player.", e);
//        }
//    }
//
    @Override
    public List<PlayerDto> findAllPlayers() throws ServiceException {
        try {
            return playerDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Players retrieving failed.", e);
            throw new ServiceException("Players retrieving failed.", e);
        }
    }
//
//    @Override
//    public PlayerDto findByNickname(String nickname) throws ServiceException {
//        try {
//            return playerDao.findByNickname(nickname);
//        } catch (DaoException e) {
//            LOGGER.error("Player retrieving failed.", e);
//            throw new ServiceException("Player retrieving failed.", e);
//        }
//    }
//
//    @Override
//    public boolean join(TournamentJoiningDto dto) throws ServiceException {
//        try {
//            return playerDao.addTournament(dto);
//        } catch (Throwable e) {
//            LOGGER.error("Failed to join tournament id and player id.", e);
//            throw new ServiceException("Failed to join tournament id and player id.", e);
//        }
//    }
}
