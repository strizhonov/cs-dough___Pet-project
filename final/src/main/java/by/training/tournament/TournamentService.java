package by.training.tournament;

import by.training.core.ServiceException;

import java.util.List;

public interface TournamentService {

    long create(TournamentDto tournamentDto) throws ServiceException;

    TournamentDto find(long id) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    List<TournamentDto> findAll() throws ServiceException;

    TournamentDto findByName(String name) throws ServiceException;

    List<TournamentDto> findAllOfOrganizer(long parseLong) throws ServiceException;

    List<TournamentDto> findAllOfPlayer(long id) throws ServiceException;

    boolean joinTournament(ParticipantDto dto) throws ServiceException;

    boolean leaveTournament(ParticipantDto dto) throws ServiceException;

}
