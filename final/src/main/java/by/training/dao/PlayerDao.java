package by.training.dao;

import by.training.dto.PlayerDto;

public interface PlayerDao extends CrudDao<PlayerDto> {

    PlayerDto findByNickname(String nickname) throws DaoException;

    boolean addTournament(long playerId, long tournamentId) throws DaoException;
}
