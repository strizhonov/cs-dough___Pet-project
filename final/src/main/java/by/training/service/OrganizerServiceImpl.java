package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.DaoException;
import by.training.dao.OrganizerDao;
import by.training.dao.UserDao;
import by.training.dto.OrganizerDto;
import by.training.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Bean
public class OrganizerServiceImpl extends BaseBeanService implements OrganizerService {

    private static Logger LOGGER = LogManager.getLogger(OrganizerServiceImpl.class);
    private OrganizerDao organizerDao;
    private UserDao userDao;

    public OrganizerServiceImpl(OrganizerDao organizerDao, UserDao userDao, TransactionManager transactionManager) {
        super(transactionManager);
        this.organizerDao = organizerDao;
        this.userDao = userDao;
    }

    public long create(OrganizerDto organizerDto, UserDto userDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long organizerId = organizerDao.save(organizerDto);
            userDto.setOrganizerId(organizerId);
            userDao.update(userDto);
            transactionManager.commitTransaction();
            return organizerId;
        } catch (Exception e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Organizer registering failed.", e);
            throw new ServiceException("Organizer registering failed.", e);
        }
    }

    @Override
    public OrganizerDto find(long id) throws ServiceException {
        try {
            return organizerDao.get(id);
        } catch (DaoException e) {
            LOGGER.error("Organizer retrieving failed.", e);
            throw new ServiceException("Organizer retrieving failed.", e);
        }
    }

    @Override
    public OrganizerDto findByName(String name) throws ServiceException {
        try {
            return organizerDao.findByName(name);
        } catch (DaoException e) {
            LOGGER.error("Organizer retrieving failed.", e);
            throw new ServiceException("Organizer retrieving failed.", e);
        }
    }

    @Override
    public boolean update(OrganizerDto organizerDto) throws ServiceException {
        try {
            return organizerDao.update(organizerDto);
        } catch (DaoException e) {
            LOGGER.error("Organizer updating failed.", e);
            throw new ServiceException("Organizer updating failed.", e);
        }
    }

}
