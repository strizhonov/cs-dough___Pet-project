package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.DaoException;
import by.training.dao.GameDao;
import by.training.dao.TournamentDao;
import by.training.dto.GameDto;
import by.training.dto.TournamentDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Bean
public class TournamentServiceImpl extends BaseBeanService implements TournamentService {

    private static Logger LOGGER = LogManager.getLogger(TournamentServiceImpl.class);
    private TournamentDao tournamentDao;
    private GameDao gameDao;

    public TournamentServiceImpl(TournamentDao tournamentDao, GameDao gameDao, TransactionManager transactionManager) {
        super(transactionManager);
        this.tournamentDao = tournamentDao;
        this.gameDao = gameDao;
    }

    @Override
    public List<TournamentDto> findAll() throws ServiceException {
        try {
            return tournamentDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Tournaments retrieving failed.", e);
            throw new ServiceException("Tournaments retrieving failed.", e);
        }
    }

    @Override
    public TournamentDto find(long id) throws ServiceException {
        try {
            return tournamentDao.get(id);
        } catch (DaoException e) {
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return tournamentDao.delete(id);
        } catch (DaoException e) {
            LOGGER.error("Tournament deleting failed.", e);
            throw new ServiceException("Tournament deleting failed.", e);
        }
    }

    @Override
    public boolean update(TournamentDto tournamentDto) throws ServiceException {
        try {
            return tournamentDao.update(tournamentDto);
        } catch (DaoException e) {
            LOGGER.error("Tournament updating failed.", e);
            throw new ServiceException("Tournament updating failed.", e);
        }
    }

    @Override
    public long create(TournamentDto tournamentDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long id = tournamentDao.save(tournamentDto);
            tournamentDto.setId(id);
            fill(tournamentDto);
            tournamentDao.update(tournamentDto);
            transactionManager.commitTransaction();
            return id;
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament creating failed.", e);
            throw new ServiceException("Tournament creating failed.", e);
        }
    }

    @Override
    public TournamentDto findByName(String name) throws ServiceException {
        try {
            return tournamentDao.findByName(name);
        } catch (Exception e) {
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }


    private void fill(TournamentDto tournamentDto) throws ServiceException {
        try {
            for (int i = 0; i < tournamentDto.getPlayersNumber() - 1; i++) {
                GameDto gameDto = new GameDto.Builder()
                        .tournamentId(tournamentDto.getId())
                        .bracketIndex(i)
                        .build();
                gameDao.saveNew(gameDto);
            }
        } catch (DaoException e) {
            LOGGER.error("Game creation failed.", e);
            throw new ServiceException("Game creation failed.", e);
        }
    }


}
