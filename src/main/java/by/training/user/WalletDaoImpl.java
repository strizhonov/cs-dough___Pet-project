package by.training.user;

import by.training.connection.ConnectionProvider;
import by.training.core.Bean;
import by.training.core.DaoException;
import by.training.core.EntityNotFoundException;
import by.training.util.DaoMapper;
import by.training.util.EntityDtoConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean
public class WalletDaoImpl implements WalletDao {

    private static final String INSERT =
            "INSERT INTO wallet (balance, currency, user_id) " +
                    "VALUES (?,?,?)";

    private static final String SELECT =
            "SELECT wallet_id, balance, currency, user_id " +
                    "FROM wallet " +
                    "WHERE wallet_id=?";

    private static final String UPDATE =
            "UPDATE wallet " +
                    "SET balance=?, currency=?, user_id=? " +
                    "WHERE wallet_id=?";

    private static final String DELETE =
            "DELETE FROM wallet " +
                    "WHERE wallet_id=?";

    private static final String SELECT_ALL =
            "SELECT wallet_id, balance, currency, user_id " +
                    "FROM wallet";

    private static final String SELECT_OF_USER =
            "SELECT wallet_id, balance, currency, user_id " +
                    "FROM wallet " +
                    "WHERE user_id=?";

    private static final String SELECT_OF_PLAYER =
            "SELECT wallet_id, balance, currency, wallet.user_id " +
                    "FROM wallet " +
                    "INNER JOIN user_account ua " +
                    "ON wallet.user_id = ua.user_id " +
                    "INNER JOIN player p on ua.user_id = p.user_account_id " +
                    "WHERE p.player_id =?";

    private static final String SELECT_OF_ORGANIZER =
            "SELECT wallet_id, balance, currency, wallet.user_id " +
                    "FROM wallet " +
                    "INNER JOIN user_account ua " +
                    "ON wallet.user_id = ua.user_id " +
                    "INNER JOIN organizer o on ua.user_id = o.user_account_id " +
                    "WHERE o.organizer_id =?";

    private static final Logger LOGGER = LogManager.getLogger(WalletDaoImpl.class);
    private final ConnectionProvider provider;


    public WalletDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
    }


    @Override
    public long save(WalletDto walletDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            Wallet entity = EntityDtoConverter.fromDto(walletDto);
            fillSaveStatement(statement, entity);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }

                return 0;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity saving.", e);
            throw new DaoException("Unable to perform entity saving.", e);
        }
    }


    @Override
    public WalletDto get(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                WalletDto walletDto = null;

                if (resultSet.next()) {
                    walletDto = DaoMapper.mapWalletDto(resultSet);
                }

                if (walletDto == null) {
                    LOGGER.error("Unable to get wallet with " + id + " id, not found.");
                    throw new DaoException("Unable to get wallet with " + id + " id, not found.");
                }

                return walletDto;

            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    @Override
    public boolean update(WalletDto walletDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            Wallet entity = EntityDtoConverter.fromDto(walletDto);
            fillUpdateStatement(statement, entity);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity updating.", e);
            throw new DaoException("Unable to perform entity updating.", e);
        }
    }


    @Override
    public boolean delete(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setLong(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity deleting.", e);
            throw new DaoException("Unable to perform entity deleting.", e);
        }
    }


    @Override
    public List<WalletDto> findAll() throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            List<WalletDto> result = new ArrayList<>();

            while (resultSet.next()) {
                WalletDto walletDto = DaoMapper.mapWalletDto(resultSet);
                result.add(walletDto);
            }

            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }


    @Override
    public WalletDto getOfUser(long userId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_OF_USER)) {

            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                WalletDto walletDto = null;

                if (resultSet.next()) {
                    walletDto = DaoMapper.mapWalletDto(resultSet);
                }

                if (walletDto == null) {
                    LOGGER.error("Unable to get wallet with id, not found.");
                    throw new DaoException("Unable to get wallet with  id, not found.");
                }

                return walletDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    @Override
    public WalletDto getOfPlayer(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_OF_PLAYER)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                WalletDto walletDto = null;

                if (resultSet.next()) {
                    walletDto = DaoMapper.mapWalletDto(resultSet);
                }

                if (walletDto == null) {
                    LOGGER.error("Unable to get wallet with id, not found.");
                    throw new EntityNotFoundException("Unable to get wallet with  id, not found.");
                }

                return walletDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    @Override
    public WalletDto getOfOrganizer(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_OF_ORGANIZER)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                WalletDto walletDto = null;

                if (resultSet.next()) {
                    walletDto = DaoMapper.mapWalletDto(resultSet);
                }

                if (walletDto == null) {
                    LOGGER.error("Unable to get wallet with id, not found.");
                    throw new EntityNotFoundException("Unable to get wallet with  id, not found.");
                }

                return walletDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private void fillSaveStatement(PreparedStatement statement, Wallet wallet) throws SQLException {
        int i = 0;
        statement.setDouble(++i, wallet.getBalance());
        statement.setString(++i, wallet.getCurrency().name());
        statement.setDouble(++i, wallet.getUserId());
    }


    private void fillUpdateStatement(PreparedStatement statement, Wallet wallet) throws SQLException {
        int i = 0;
        statement.setDouble(++i, wallet.getBalance());
        statement.setString(++i, wallet.getCurrency().name());
        statement.setDouble(++i, wallet.getUserId());
        statement.setLong(++i, wallet.getId());
    }

}
