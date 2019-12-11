package by.training.user;

import by.training.common.CrudDao;
import by.training.common.DaoException;

public interface UserDao extends CrudDao<UserDto> {

    UserDto login(LoginDto loginDto) throws DaoException;

    UserDto findByUsername(String username) throws DaoException;

    UserDto findByEmail(String email) throws DaoException;

    boolean updatePlayerId(UserDto userDto) throws DaoException;
}
