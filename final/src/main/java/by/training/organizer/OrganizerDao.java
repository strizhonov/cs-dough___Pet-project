package by.training.organizer;

import by.training.core.CrudDao;
import by.training.core.DaoException;

public interface OrganizerDao extends CrudDao<OrganizerDto> {

    OrganizerDto getByName(String name) throws DaoException;

    OrganizerDto getByUserId(long id) throws DaoException;
}
