package by.training.service;

import by.training.dto.PlayerDto;
import by.training.dto.TournamentJoiningDto;
import by.training.dto.UserDto;

import java.util.List;

public interface PlayerService {

    long create(PlayerDto playerDto, UserDto userDto) throws ServiceException;

    PlayerDto find(long id) throws ServiceException;

    boolean update(PlayerDto entity) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    List<PlayerDto> findAll() throws ServiceException;

    PlayerDto findByNickname(String nickname) throws ServiceException;

    boolean join(TournamentJoiningDto dto) throws ServiceException;
}
