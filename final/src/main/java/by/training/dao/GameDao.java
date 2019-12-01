package by.training.dao;

import by.training.dto.GameDto;

import java.util.List;

public interface GameDao extends CrudDao<GameDto> {

    long saveNew(GameDto gameDto) throws DaoException;

    List<GameDto> findLatest(int maxGameResults) throws DaoException;
}
