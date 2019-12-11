package by.training.game;

import by.training.common.ServiceException;

import java.util.List;

public interface GameService {

    GameDto find(long id) throws ServiceException;

    boolean update(GameDto gameDto) throws ServiceException;

    List<GameDto> findAll() throws ServiceException;

    List<GameDto> findLatest(int maxGameResults) throws ServiceException;

    List<GameDto> getAllOfTournament(long id) throws ServiceException;

}
