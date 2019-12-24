package by.training.organizer;

import by.training.connection.TransactionManager;
import by.training.core.*;
import by.training.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Bean
public class OrganizerServiceImpl extends BaseBeanService implements OrganizerService {

    private final static Logger LOGGER = LogManager.getLogger(OrganizerServiceImpl.class);
    private final OrganizerDao organizerDao;


    public OrganizerServiceImpl(OrganizerDao organizerDao, TransactionManager transactionManager) {
        super(transactionManager);
        this.organizerDao = organizerDao;
    }


    @Override
    public long create(OrganizerDto organizerDto) throws ServiceException {
        try {

            return organizerDao.save(organizerDto);

        } catch (DaoException e) {
            LOGGER.error("Organizer saving failed.", e);
            throw new ServiceException("Organizer saving failed.", e);
        }
    }


    @Override
    public OrganizerDto find(long id) throws ServiceException {
        try {

            return organizerDao.get(id);

        } catch (EntityNotFoundException e) {
            LOGGER.error("Organizer with id " + id + " not found.", e);
            return null;

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
            LOGGER.error("Organizer retrieving failed.", e);
            throw new ServiceException("Organizer retrieving failed.", e);
        }
    }


    @Override
    public boolean delete(long organizerId) throws ServiceException {
        try {

            return organizerDao.delete(organizerId);

        } catch (DaoException e) {
            LOGGER.error("Organizer deleting failed.", e);
            throw new ServiceException("Organizer deleting failed.", e);
        }
    }


    @Override
    public OrganizerDto findByName(String name) throws ServiceException {
        try {

            return organizerDao.getByName(name);

        } catch (EntityNotFoundException e) {
            LOGGER.error("Organizer with id " + name + " not found.", e);
            return null;

        } catch (DaoException e) {
            LOGGER.error("Organizer deleting failed.", e);
            throw new ServiceException("Organizer deleting failed.", e);
        }

    }

    @Override
    public OrganizerDto findOfUser(UserDto user) throws ServiceException {
        try {

            return organizerDao.getByUserId(user.getId());

        } catch (EntityNotFoundException e) {
            LOGGER.error("Organizer not found.", e);
            return null;

        } catch (DaoException e) {
            LOGGER.error("Organizer deleting failed.", e);
            throw new ServiceException("Organizer deleting failed.", e);
        }
    }


}
