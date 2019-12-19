package by.training.game;

import by.training.core.ServiceException;
import by.training.tournament.TournamentDto;
import by.training.tournament.TournamentPlacement;

import java.util.List;

public interface GameService {

    ComplexGameDto find(long gameId) throws ServiceException;

    List<ComplexGameDto> findAll() throws ServiceException;

    void increaseFirstPlayerPoints(long gameId) throws ServiceException;

    void increaseSecondPlayerPoints(long gameId) throws ServiceException;

    List<ComplexGameDto> findAllOfPlayer(long id) throws ServiceException;

    List<ComplexGameDto> findAllOfTournament(long id) throws ServiceException;

    List<ComplexGameDto> findLatest(int maxGameResults) throws ServiceException;

    List<TournamentPlacement> findTournamentPlacements(TournamentDto tournament) throws ServiceException;

}
