package by.training.game;

import by.training.common.CrudDao;
import by.training.common.DaoException;

public interface GameDao extends CrudDao<GameDto> {

    void saveNew(GameDto gameDto) throws DaoException;

}
