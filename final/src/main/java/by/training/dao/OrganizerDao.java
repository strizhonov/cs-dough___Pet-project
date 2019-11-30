package by.training.dao;

import by.training.dto.OrganizerDto;

public interface OrganizerDao extends CrudDao<OrganizerDto> {
    OrganizerDto findByName(String name) throws DaoException;
}
