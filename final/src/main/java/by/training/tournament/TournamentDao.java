package by.training.tournament;

import by.training.common.CrudDao;
import by.training.common.DaoException;

public interface TournamentDao extends CrudDao<TournamentDto> {

    TournamentDto findByName(String name) throws DaoException;

}
