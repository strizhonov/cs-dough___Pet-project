package by.training.player;

import by.training.connection.TransactionManager;
import by.training.core.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Bean
public class PlayerServiceImpl extends BaseBeanService implements PlayerService {

    private final static Logger LOGGER = LogManager.getLogger(PlayerServiceImpl.class);
    private final PlayerDao playerDao;


    public PlayerServiceImpl(PlayerDao playerDao, TransactionManager transactionManager) {
        super(transactionManager);
        this.playerDao = playerDao;
    }


    @Override
    public long create(PlayerDto playerDto) throws ServiceException {
        try {

            return playerDao.save(playerDto);

        } catch (DaoException e) {
            LOGGER.error("Unable to save player.", e);
            throw new ServiceException("Unable to save player.", e);
        }

    }


    @Override
    public PlayerDto find(long id) throws ServiceException {
        try {

            return playerDao.get(id);

        } catch (EntityNotFoundException e) {
            LOGGER.warn("Player not found.", e);
            return null;
        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }

    @Override
    public PlayerDto findOfUser(long userId) throws ServiceException {
        try {

            return playerDao.getByUserId(userId);

        } catch (EntityNotFoundException e) {
            LOGGER.warn("Player not found.", e);
            return null;
        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }


    @Override
    public boolean update(PlayerDto genericDto) throws ServiceException {
        try {

            return playerDao.update(genericDto);

        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }


    @Override
    public boolean delete(long id) throws ServiceException {
        try {

            return playerDao.delete(id);

        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
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

            return playerDao.getByNickname(nickname);

        } catch (EntityNotFoundException e) {
            LOGGER.warn("Player with " + nickname + " not found.", e);
            return null;

        } catch (DaoException e) {
            LOGGER.error("Unable to get player.", e);
            throw new ServiceException("Unable to get player.", e);
        }
    }


}
