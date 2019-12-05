package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.DaoException;
import by.training.dao.GameDao;
import by.training.dto.GameDto;
import by.training.dto.TournamentDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

@Bean
public class GameServiceImpl extends BaseBeanService implements GameService {

    private static Logger LOGGER = LogManager.getLogger(GameServiceImpl.class);
    private GameDao gameDao;

    public GameServiceImpl(GameDao gameDao, TransactionManager transactionManager) {
        super(transactionManager);
        this.gameDao = gameDao;
    }

    @Override
    public GameDto find(long id) throws ServiceException {
        try {
            return gameDao.get(id);
        } catch (DaoException e) {
            LOGGER.error("Game retrieving failed.", e);
            throw new ServiceException("Game retrieving failed.", e);
        }
    }

    @Override
    public boolean update(GameDto gameDto) throws ServiceException {
        try {
            return gameDao.update(gameDto);
        } catch (Throwable e) {
            LOGGER.error("Game updating failed.", e);
            throw new ServiceException("Game updating failed.", e);
        }
    }

    @Override
    public List<GameDto> findLatest(int maxGameResults) throws ServiceException {
        try {
            return gameDao.findAll().stream()
                    .filter(game -> game.getEndTime() != null && game.getEndTime().before(new Date()))
                    .sorted(Comparator.comparing(GameDto::getEndTime).reversed())
                    .limit(maxGameResults)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            LOGGER.error("Game retrieving failed.", e);
            throw new ServiceException("Game retrieving failed.", e);
        }
    }

}
