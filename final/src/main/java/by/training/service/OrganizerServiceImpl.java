package by.training.service;

import by.training.appentry.Bean;
import by.training.connection.TransactionManager;
import by.training.dao.OrganizerDao;
import by.training.dto.OrganizerDto;
import by.training.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Bean
public class OrganizerServiceImpl extends BaseBeanService implements OrganizerService {

    private static Logger LOGGER = LogManager.getLogger(OrganizerServiceImpl.class);
    private OrganizerDao organizerDao;
    private UserService userService;

    public OrganizerServiceImpl(OrganizerDao organizerDao, UserService userService, TransactionManager transactionManager) {
        super(transactionManager);
        this.organizerDao = organizerDao;
        this.userService = userService;
    }

    public long create(OrganizerDto organizerDto, UserDto userDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            long organizerId = organizerDao.save(organizerDto);
            userDto.setOrganizerId(organizerId);
            userService.update(userDto);
            transactionManager.commitTransaction();
            return organizerId;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Organizer registering failed.", e);
            throw new ServiceException("Organizer registering failed.", e);
        }
    }

    @Override
    public OrganizerDto find(long id) throws ServiceException {
        try {
            transactionManager.startTransaction();
            OrganizerDto organizerDto = organizerDao.get(id);
            transactionManager.commitTransaction();
            return organizerDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Organizer retrieving failed.", e);
            throw new ServiceException("Organizer retrieving failed.", e);
        }
    }

    @Override
    public OrganizerDto findByName(String name) throws ServiceException {
        try {
            transactionManager.startTransaction();
            OrganizerDto organizerDto = organizerDao.findByName(name);
            transactionManager.commitTransaction();
            return organizerDto;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Organizer retrieving failed.", e);
            throw new ServiceException("Organizer retrieving failed.", e);
        }
    }

    @Override
    public boolean update(OrganizerDto organizerDto) throws ServiceException {
        try {
            transactionManager.startTransaction();
            boolean success = organizerDao.update(organizerDto);
            transactionManager.commitTransaction();
            return success;
        } catch (Throwable e) {
            transactionManager.rollbackTransaction();
            LOGGER.error("Organizer updating failed.", e);
            throw new ServiceException("Organizer updating failed.", e);
        }
    }

}
