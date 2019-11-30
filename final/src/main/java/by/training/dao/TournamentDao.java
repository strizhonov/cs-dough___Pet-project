package by.training.dao;

import by.training.dto.TournamentDto;

public interface TournamentDao extends CrudDao<TournamentDto> {

    TournamentDto findByName(String name) throws DaoException;

}
