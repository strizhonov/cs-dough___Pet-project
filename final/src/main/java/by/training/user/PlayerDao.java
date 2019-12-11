package by.training.user;

import by.training.common.CrudDao;
import by.training.common.DaoException;
import by.training.tournament.TournamentJoiningDto;

public interface PlayerDao extends CrudDao<PlayerDto> {

    PlayerDto findByNickname(String nickname) throws DaoException;

    boolean addTournament(TournamentJoiningDto dto) throws DaoException;
}
