package by.training.game;

import by.training.core.CrudDao;
import by.training.core.DaoException;

import java.util.List;

public interface GameDao extends CrudDao<PlainGameDto> {

    long saveNew(PlainGameDto game) throws DaoException;

    ComplexGameDto getComplex(long gameId) throws DaoException;

    ComplexGameDto getComplexByBracketIndex(int nextGameIndex, long tournamentId) throws DaoException;

    List<ComplexGameDto> findAllComplex() throws DaoException;

    List<ComplexGameDto> findAllComplexOfPlayer(long id) throws DaoException;

    List<ComplexGameDto> findAllComplexOfTournament(long tournamentId) throws DaoException;

}
