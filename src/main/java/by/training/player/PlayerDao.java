package by.training.player;

import by.training.core.CrudDao;
import by.training.core.DaoException;

public interface PlayerDao extends CrudDao<PlayerDto> {

    PlayerDto getByNickname(String nickname) throws DaoException;

    PlayerDto getByUserId(long userId) throws DaoException;
}
