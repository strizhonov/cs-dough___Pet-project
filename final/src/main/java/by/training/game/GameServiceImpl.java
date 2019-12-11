package by.training.game;

import by.training.common.BaseBeanService;
import by.training.core.Bean;
import by.training.connection.TransactionManager;
import by.training.common.ServiceException;
import by.training.common.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Bean
public class GameServiceImpl extends BaseBeanService implements GameService {

    private static final Logger LOGGER = LogManager.getLogger(GameServiceImpl.class);
    private final GameDao gameDao;

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

    @Override
    public List<GameDto> getAllOfTournament(long tournamentId) throws ServiceException {
        try {
            return gameDao.findAll().stream()
                    .filter(game -> game.getTournamentId() == tournamentId)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            LOGGER.error("Game retrieving failed.", e);
            throw new ServiceException("Game retrieving failed.", e);
        }
    }

    @Override
    public List<GameDto> findAll() throws ServiceException {
        try {
            return gameDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Games retrieving failed.", e);
            throw new ServiceException("Games retrieving failed.", e);
        }
    }

}
