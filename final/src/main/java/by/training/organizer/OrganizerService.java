package by.training.organizer;

import by.training.core.ServiceException;

public interface OrganizerService {

    long create(OrganizerDto organizerDto) throws ServiceException;

    OrganizerDto find(long id) throws ServiceException;

    boolean update(OrganizerDto organizerDto) throws ServiceException;

    boolean delete(long id) throws ServiceException;

    OrganizerDto findByName(String name) throws ServiceException;

}
