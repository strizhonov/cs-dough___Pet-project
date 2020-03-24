package by.training.user;

import by.training.core.CrudDao;
import by.training.core.DaoException;

public interface UserDao extends CrudDao<UserDto> {

    UserDto login(LoginDto loginDto) throws DaoException;

    UserDto findByUsername(String username) throws DaoException;

    UserDto findByEmail(String email) throws DaoException;

}
