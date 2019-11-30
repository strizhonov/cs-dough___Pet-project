package by.training.service;

import by.training.dto.UserDto;

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

}
