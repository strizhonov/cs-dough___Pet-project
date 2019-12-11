package by.training.user;

import by.training.common.ServiceException;
import by.training.tournament.TournamentJoiningDto;

import java.util.List;

public interface UserService {

    UserDto findBy(String login, String password) throws ServiceException;

    long registerUser(UserDto userDto) throws ServiceException;

    List<UserDto> getAll() throws ServiceException;

    UserDto find(long id) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    boolean update(UserDto userDto) throws ServiceException;

    UserDto findByUsername(String username) throws ServiceException;

    UserDto findByEmail(String email) throws ServiceException;

    PlayerDto findPlayerByNickname(String nickname) throws ServiceException;

    long createPlayer(PlayerDto playerDto) throws ServiceException;

    boolean joinTournament(TournamentJoiningDto dto) throws ServiceException;

    PlayerDto findPlayer(long id) throws ServiceException;

    OrganizerDto findOrganizerByName(String name) throws ServiceException;

    long createOrganizer(OrganizerDto organizerDto) throws ServiceException;

    OrganizerDto findOrganizer(long id) throws ServiceException;

    List<PlayerDto> findAllPlayers() throws ServiceException;

}
