package by.training.dao;

import by.training.dto.GameDto;

public interface GameDao extends CrudDao<GameDto> {

    long saveNew(GameDto gameDto) throws DaoException;

}
