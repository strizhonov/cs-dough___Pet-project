package by.training.game;

import by.training.core.CrudDao;
import by.training.core.DaoException;

public interface GameServerDao extends CrudDao<GameServerDto> {

    GameServerDto getByGameId(long gameId) throws DaoException;

    void deleteByGameId(long gameId) throws DaoException;
}
