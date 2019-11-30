package by.training.service;

import by.training.dto.TournamentDto;

import java.util.List;

public interface TournamentService {

    List<TournamentDto> findAll() throws ServiceException;

    TournamentDto find(long id) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    boolean update(TournamentDto tournament) throws ServiceException;

    long create(TournamentDto tournamentDto) throws ServiceException;

    TournamentDto findByName(String name) throws ServiceException;

}
