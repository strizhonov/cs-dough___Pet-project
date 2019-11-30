package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.TournamentDao;
import by.training.dto.TournamentDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Bean
public class TournamentServiceImpl extends BaseBeanService implements TournamentService {

    private static Logger LOGGER = LogManager.getLogger(TournamentServiceImpl.class);
    private TournamentDao tournamentDao;
    private GameService gameService;

    public TournamentServiceImpl(TournamentDao tournamentDao, GameService gameService, TransactionManager transactionManager) {
        super(transactionManager);
        this.tournamentDao = tournamentDao;
        this.gameService = gameService;
    }

    @Override
    public List<TournamentDto> findAll() throws ServiceException {
        try {
            transactionManager.startTransaction();
            List<TournamentDto> result = tournamentDao.findAll();
            transactionManager.commitTransaction();
            return result;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournaments retrieving failed.", e);
            throw new ServiceException("Tournaments retrieving failed.", e);
        }
    }

    @Override
    public TournamentDto find(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            TournamentDto tournamentDto = tournamentDao.get(id);
            transactionManager.commitTransaction();
            return tournamentDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean result = tournamentDao.delete(id);
            transactionManager.commitTransaction();
            return result;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament deleting failed.", e);
            throw new ServiceException("Tournament deleting failed.", e);
        }
    }

    @Override
    public boolean update(TournamentDto tournamentDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean result = tournamentDao.update(tournamentDto);
            transactionManager.commitTransaction();
            return result;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
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
            gameService.fill(tournamentDto);
            tournamentDao.update(tournamentDto);
            transactionManager.commitTransaction();
            return id;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament creating failed.", e);
            throw new ServiceException("Tournament creating failed.", e);
        }
    }

    @Override
    public TournamentDto findByName(String name) throws ServiceException {
        try {
            transactionManager.startTransaction();
            TournamentDto tournamentDto = tournamentDao.findByName(name);
            transactionManager.commitTransaction();
            return tournamentDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Tournament retrieving failed.", e);
            throw new ServiceException("Tournament retrieving failed.", e);
        }
    }

}
