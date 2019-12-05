package by.training.service;

import by.training.dto.GameDto;
import by.training.dto.TournamentDto;

import java.util.List;

public interface GameService {

    GameDto find(long id) throws ServiceException;

    boolean update(GameDto gameDto) throws ServiceException;

    List<GameDto> findLatest(int maxGameResults) throws ServiceException;
}
