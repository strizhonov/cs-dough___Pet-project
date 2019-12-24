package by.training.player;

import by.training.core.ServiceException;

import java.util.List;

public interface PlayerService {

    long create(PlayerDto playerDto) throws ServiceException;

    PlayerDto find(long id) throws ServiceException;

    PlayerDto findOfUser(long userId) throws ServiceException;

    boolean update(PlayerDto genericDto) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    List<PlayerDto> findAll() throws ServiceException;

    PlayerDto findByNickname(String nickname) throws ServiceException;

}
