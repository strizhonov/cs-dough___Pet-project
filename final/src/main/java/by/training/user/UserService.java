package by.training.user;

import by.training.core.ServiceException;

import java.util.List;

public interface UserService {

    void registerUser(RegistrationDto registrationDto) throws ServiceException;

    UserDto login(LoginDto loginDto) throws ServiceException;

    UserDto find(long id) throws ServiceException;

    boolean update(UserDto userDto) throws ServiceException;

    List<UserDto> findAll() throws ServiceException;

    UserDto findByUsername(String username) throws ServiceException;

    UserDto findByEmail(String email) throws ServiceException;

    void deposit(double value, UserDto user) throws ServiceException;

    boolean withdraw(double value, UserDto user) throws ServiceException;

}
