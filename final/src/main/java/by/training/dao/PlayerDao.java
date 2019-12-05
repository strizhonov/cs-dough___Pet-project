package by.training.dao;

import by.training.dto.TournamentJoiningDto;
import by.training.dto.PlayerDto;

public interface PlayerDao extends CrudDao<PlayerDto> {

    PlayerDto findByNickname(String nickname) throws DaoException;

    boolean addTournament(TournamentJoiningDto dto) throws DaoException;
}
