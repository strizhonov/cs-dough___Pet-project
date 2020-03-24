package by.training.game;

import by.training.core.ServiceException;
import by.training.tournament.TournamentDto;
import by.training.tournament.TournamentPlacement;

import java.util.List;

public interface GameService {

    ComplexGameDto find(long gameId) throws ServiceException;

    List<ComplexGameDto> findAll() throws ServiceException;

    /**
     * Performing emulation of winning round by player.
     *
     * @param gameId     id of game count to increase
     * @param playerSlot index of player within the game (0 or 1).
     * @return true if tournament finished after points had been increased
     */
    boolean increasePlayerPoints(long gameId, int playerSlot) throws ServiceException;

    List<ComplexGameDto> findAllOfPlayer(long id) throws ServiceException;

    List<ComplexGameDto> findAllOfTournament(long id) throws ServiceException;

    List<ComplexGameDto> findLatest(int maxGameResults) throws ServiceException;

    List<TournamentPlacement> findTournamentPlacements(TournamentDto tournament) throws ServiceException;

}
