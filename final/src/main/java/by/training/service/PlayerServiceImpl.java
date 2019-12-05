package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.DaoException;
import by.training.dao.PlayerDao;
import by.training.dto.PlayerDto;
import by.training.dto.TournamentJoiningDto;
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
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Unable to save player.", e);
            throw new ServiceException("Unable to save player.", e);
        }
    }

    @Override
    public PlayerDto find(long id) throws ServiceException {
        try {
            return playerDao.get(id);
        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }

    @Override
    public boolean update(PlayerDto playerDto) throws ServiceException {
        try {
            return playerDao.update(playerDto);
        } catch (DaoException e) {
            LOGGER.error("Unable to update player.", e);
            throw new ServiceException("Unable to update player.", e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return playerDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("Unable to delete player.", e);
            throw new ServiceException("Unable to delete player.", e);
        }
    }

    @Override
    public List<PlayerDto> findAll() throws ServiceException {
        try {
            return playerDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Players retrieving failed.", e);
            throw new ServiceException("Players retrieving failed.", e);
        }
    }

    @Override
    public PlayerDto findByNickname(String nickname) throws ServiceException {
        try {
            return playerDao.findByNickname(nickname);
        } catch (DaoException e) {
            LOGGER.error("Player retrieving failed.", e);
            throw new ServiceException("Player retrieving failed.", e);
        }
    }

    @Override
    public boolean join(TournamentJoiningDto dto) throws ServiceException {
        try {
            return playerDao.addTournament(dto);
        } catch (Throwable e) {
            LOGGER.error("Failed to join tournament id and player id.", e);
            throw new ServiceException("Failed to join tournament id and player id.", e);
        }
    }
}
