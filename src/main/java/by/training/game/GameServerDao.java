package by.training.game;

import by.training.core.CrudDao;
import by.training.core.DaoException;

public interface GameServerDao extends CrudDao<GameServerDto> {

    void deleteByGameId(long gameId) throws DaoException;
}
