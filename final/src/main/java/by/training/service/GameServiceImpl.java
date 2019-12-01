package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
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
            transactionManager.startTransaction();
            GameDto gameDto = gameDao.get(id);
            transactionManager.commitTransaction();
            return gameDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Game retrieving failed.", e);
            throw new ServiceException("Game retrieving failed.", e);
        }
    }

    @Override
    public boolean update(GameDto gameDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = gameDao.update(gameDto);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Game updating failed.", e);
            throw new ServiceException("Game updating failed.", e);
        }
    }

    @Override
    public void fill(TournamentDto tournamentDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            for (int i = 0; i < tournamentDto.getPlayersNumber() - 1; i++) {
                GameDto gameDto = GameDto.Builder.aGameDto()
                        .tournamentId(tournamentDto.getId())
                        .bracketIndex(i)
                        .build();
                gameDao.saveNew(gameDto);
            }
            transactionManager.commitTransaction();
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Game creation failed.", e);
            throw new ServiceException("Game creation failed.", e);
        }

    }

    @Override
    public List<GameDto> findLatest(int maxGameResults) throws ServiceException {
        try {
            transactionManager.startTransaction();
            Date now = new Date();
            List<GameDto> result = gameDao.findAll().stream()
                    .filter(game -> game.getEndTime() != null && game.getEndTime().before(now))
                    .sorted(Comparator.comparing(GameDto::getEndTime).reversed())
                    .limit(maxGameResults)
                    .collect(Collectors.toList());
            transactionManager.commitTransaction();
            return result;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Game retrieving failed.", e);
            throw new ServiceException("Game retrieving failed.", e);
        }
    }

}
