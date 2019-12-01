package by.training.dao;

import by.training.appentry.Bean;
import by.training.connection.ConnectionProvider;
import by.training.dto.WalletDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean
public class WalletDaoImpl implements WalletDao {

    private static final String INSERT =
            "INSERT INTO wallet (currency, balance) " +
                    "VALUES (?,?)";
    private static final String SELECT =
            "SELECT wallet.wallet_id, currency, balance, u.user_id " +
                    "FROM wallet " +
                    "INNER JOIN user_account u " +
                    "ON wallet.wallet_id = u.wallet_id " +
                    "WHERE wallet.wallet_id = ?";
    private static final String UPDATE =
            "UPDATE wallet " +
                    "SET currency=?, balance=? " +
                    "WHERE wallet_id = ?";
    private static final String DELETE =
            "DELETE FROM wallet " +
                    "WHERE wallet_id = ?";
    private static final String SELECT_ALL =
            "SELECT wallet.wallet_id, currency, balance, u.user_id " +
                    "FROM wallet " +
                    "INNER JOIN user_account u " +
                    "ON wallet.wallet_id = u.wallet_id";

    private static final Logger LOGGER = LogManager.getLogger(WalletDaoImpl.class);
    private ConnectionProvider provider;
    private DaoMapper mapper;

    public WalletDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
        this.mapper = new DaoMapper();
    }

    @Override
    public long save(WalletDto walletDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            fillSaveStatement(statement, walletDto);
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
                    walletDto = mapper.mapWalletDto(resultSet);
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

            fillUpdateStatement(statement, walletDto);
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
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            List<WalletDto> result = new ArrayList<>();
            while (resultSet.next()) {
                WalletDto walletDto = mapper.mapWalletDto(resultSet);
                result.add(walletDto);
            }
            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    private void fillSaveStatement(PreparedStatement statement, WalletDto wallet) throws SQLException {
        int i = 0;
        statement.setString(++i, wallet.getCurrency().name());
        statement.setDouble(++i, wallet.getBalance());
    }


    private void fillUpdateStatement(PreparedStatement statement, WalletDto wallet) throws SQLException {
        int i = 0;
        statement.setString(++i, wallet.getCurrency().name());
        statement.setDouble(++i, wallet.getBalance());
        statement.setLong(++i, wallet.getId());
    }

}
