package by.training.tournament;

import by.training.core.CrudDao;
import by.training.core.DaoException;

import java.util.List;

public interface TournamentDao extends CrudDao<TournamentDto> {

    boolean isParticipantPresent(ParticipantDto dto) throws DaoException;

    void addParticipant(ParticipantDto dto) throws DaoException;

    void removeParticipant(ParticipantDto dto) throws DaoException;

    TournamentDto findByName(String name) throws DaoException;

    List<TournamentDto> findAllOfPlayer(long playerId) throws DaoException;

    List<TournamentDto> findAllOfOrganizer(long organizerId) throws DaoException;

}
