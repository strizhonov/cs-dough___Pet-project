package by.training.user;

import by.training.common.CrudDao;
import by.training.common.DaoException;

public interface OrganizerDao extends CrudDao<OrganizerDto> {
    OrganizerDto findByName(String name) throws DaoException;
}
