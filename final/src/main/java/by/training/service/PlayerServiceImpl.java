package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.PlayerDao;
import by.training.dto.PlayerDto;
import by.training.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Bean
public class PlayerServiceImpl extends BaseBeanService implements PlayerService {

    private static Logger LOGGER = LogManager.getLogger(PlayerServiceImpl.class);
    private PlayerDao playerDao;
    private UserService userService;

    public PlayerServiceImpl(PlayerDao playerDao, UserService userService, TransactionManager transactionManager) {
        super(transactionManager);
        this.playerDao = playerDao;
        this.userService = userService;
    }

    @Override
    public long create(PlayerDto playerDto, UserDto userDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long playerId = playerDao.save(playerDto);
            userDto.setPlayerAccountId(playerId);
            userService.update(userDto);
            transactionManager.commitTransaction();
            return playerId;
        } catch (Throwable e) {
            LOGGER.error("Unable to save player.", e);
            throw new ServiceException("Unable to save player.", e);
        }
    }

    @Override
    public PlayerDto find(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            PlayerDto playerDto = playerDao.get(id);
            transactionManager.commitTransaction();
            return playerDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }

    @Override
    public boolean update(PlayerDto playerDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = playerDao.update(playerDto);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Unable to update player.", e);
            throw new ServiceException("Unable to update player.", e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = playerDao.delete(id);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Unable to delete player.", e);
            throw new ServiceException("Unable to delete player.", e);
        }
    }

    @Override
    public List<PlayerDto> findAll() throws ServiceException {
        try {
            transactionManager.startTransaction();
            List<PlayerDto> result = playerDao.findAll();
            transactionManager.commitTransaction();
            return result;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Players retrieving failed.", e);
            throw new ServiceException("Players retrieving failed.", e);
        }
    }

    @Override
    public PlayerDto findByNickname(String nickname) throws ServiceException {
        try {
            transactionManager.startTransaction();
            PlayerDto playerDto = playerDao.findByNickname(nickname);
            transactionManager.commitTransaction();
            return playerDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Player retrieving failed.", e);
            throw new ServiceException("Player retrieving failed.", e);
        }
    }

    @Override
    public boolean join(long playerId, long tournamentId) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = playerDao.addTournament(playerId, tournamentId);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Failed to join tournament id and player id.", e);
            throw new ServiceException("Failed to join tournament id and player id.", e);
        }
    }
}
