package by.training.service;

import by.training.dto.GameDto;
import by.training.dto.TournamentDto;

public interface GameService {

    GameDto find(long id) throws ServiceException;

    boolean update(GameDto gameDto) throws ServiceException;

    void fill(TournamentDto tournamentDto) throws ServiceException;
}
