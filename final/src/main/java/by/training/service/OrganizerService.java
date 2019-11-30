package by.training.service;

import by.training.dto.OrganizerDto;
import by.training.dto.UserDto;

public interface OrganizerService {
    long create(OrganizerDto organizerDto, UserDto userDto) throws ServiceException;

    OrganizerDto find(long id) throws ServiceException;

    OrganizerDto findByName(String name) throws ServiceException;

    boolean update(OrganizerDto organizerDto) throws ServiceException;
}
