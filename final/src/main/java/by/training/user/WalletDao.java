package by.training.user;

import by.training.core.CrudDao;
import by.training.core.DaoException;

public interface WalletDao extends CrudDao<WalletDto> {

    WalletDto getOfUser(long id) throws DaoException;

    WalletDto getOfPlayer(long id) throws DaoException;

    WalletDto getOfOrganizer(long id) throws DaoException;

}
