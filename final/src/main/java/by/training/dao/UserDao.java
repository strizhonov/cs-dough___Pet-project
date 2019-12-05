package by.training.dao;

import by.training.dto.LoginDto;
import by.training.dto.UserDto;

public interface UserDao extends CrudDao<UserDto> {

    UserDto login(LoginDto loginDto) throws DaoException;

    UserDto findByUsername(String username) throws DaoException;

    UserDto findByEmail(String email) throws DaoException;
}
