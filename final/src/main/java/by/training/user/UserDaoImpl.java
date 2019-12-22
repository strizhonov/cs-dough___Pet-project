package by.training.user;

import by.training.connection.ConnectionProvider;
import by.training.core.Bean;
import by.training.core.DaoException;
import by.training.core.EntityNotFoundException;
import by.training.util.DaoMapper;
import by.training.util.EntityDtoConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean
public class UserDaoImpl implements UserDao {

    private static final String INSERT =
            "INSERT INTO user_account (account_avatar, username, user_password, password_key, user_email, user_type, lang) " +
                    "VALUES (?,?,?,?,?,?,?)";

    private static final String SELECT =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE user_account.user_id=?";

    private static final String UPDATE =
            "UPDATE user_account " +
                    "SET account_avatar=?, username=?, user_password=?, password_key=?, user_email=?, user_type=?, lang=? " +
                    "WHERE user_id = ?";

    private static final String DELETE =
            "DELETE FROM user_account " +
                    "WHERE user_id = ?";

    private static final String SELECT_ALL =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id";


    private static final String SELECT_BY_USERNAME =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE username=?";

    private static final String SELECT_BY_EMAIL =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE user_email=?";

    private static final String SELECT_BY_USERNAME_AND_PASSWORD =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE username=? AND user_password=?";

    private static final String SELECT_WALLET =
            "SELECT wallet_id, balance, currency, user_id " +
                    "FROM wallet " +
                    "WHERE user_id=?";


    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private final ConnectionProvider provider;


    public UserDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
    }


    @Override
    public long save(UserDto userDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            User entity = EntityDtoConverter.fromDto(userDto);
            fillSaveStatement(statement, entity);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }

                return 0;
            }

        } catch (SQLException | IOException e) {
            LOGGER.error("Unable to perform entity saving.", e);
            throw new DaoException("Unable to perform entity saving.", e);
        }
    }


    @Override
    public UserDto get(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return compile(resultSet);
                } else {
                    LOGGER.error("Unable to get user with " + id + " id, not found.");
                    throw new EntityNotFoundException("Unable to get user with " + id + " id not found.");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    @Override
    public boolean update(UserDto userDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            User entity = EntityDtoConverter.fromDto(userDto);
            fillUpdateStatement(statement, entity);
            return statement.executeUpdate() > 0;

        } catch (SQLException | IOException e) {
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
    public List<UserDto> findAll() throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            List<UserDto> result = new ArrayList<>();

            if (resultSet.next()) {
                UserDto userDto = compile(resultSet);
                result.add(userDto);
            }

            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }


    @Override
    public UserDto login(LoginDto loginDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME_AND_PASSWORD)) {

            int i = 0;
            statement.setString(++i, loginDto.getUsername());
            statement.setString(++i, loginDto.getPassword());

            try (ResultSet resultSet = statement.executeQuery()) {
                UserDto userDto = null;

                if (resultSet.next()) {
                    userDto = compile(resultSet);
                }

                if (userDto == null) {
                    LOGGER.error("Unable to login, user not found.");
                    throw new DaoException("Unable to login, user not found.");
                }

                return userDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform login.", e);
            throw new DaoException("Unable to perform login.", e);
        }
    }


    @Override
    public UserDto findByUsername(String username) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {

            int i = 0;
            statement.setString(++i, username);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return compile(resultSet);
                }

                return null;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform user search.", e);
            throw new DaoException("Unable to perform user search.", e);
        }
    }


    @Override
    public UserDto findByEmail(String email) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {

            int i = 0;
            statement.setString(++i, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                UserDto userDto = null;
                if (resultSet.next()) {
                    userDto = compile(resultSet);
                }

                return userDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform user search.", e);
            throw new DaoException("Unable to perform user search.", e);
        }
    }


    private UserDto compile(ResultSet resultSet) throws SQLException, DaoException {
        UserDto user = DaoMapper.mapUserDto(resultSet);

        WalletDto walletDto = getWallet(user.getId());
        user.setWallet(walletDto);

        return user;
    }


    private void fillSaveStatement(PreparedStatement statement, User user) throws SQLException, IOException {
        int i = 0;
        byte[] avatar = user.getAvatar();
        try (InputStream stream = new ByteArrayInputStream(avatar)) {
            statement.setBlob(++i, stream);
            statement.setString(++i, user.getUsername());
            statement.setString(++i, user.getPassword());
            statement.setString(++i, user.getPasswordKey());
            statement.setString(++i, user.getEmail());
            statement.setString(++i, user.getType().name());
            statement.setString(++i, user.getLanguage().name());
        }
    }


    private void fillUpdateStatement(PreparedStatement statement, User user) throws SQLException, IOException {
        int i = 0;
        byte[] avatar = user.getAvatar();
        try (InputStream stream = new ByteArrayInputStream(avatar)) {
            statement.setBlob(++i, stream);
            statement.setString(++i, user.getUsername());
            statement.setString(++i, user.getPassword());
            statement.setString(++i, user.getPasswordKey());
            statement.setString(++i, user.getEmail());
            statement.setString(++i, user.getType().name());
            statement.setString(++i, user.getLanguage().name());
            statement.setLong(++i, user.getId());
        }
    }


    private WalletDto getWallet(long userId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_WALLET)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return DaoMapper.mapWalletDto(resultSet);
                } else {
                    LOGGER.error("Unable to get wallet with " + userId + " user Id, not found.");
                    throw new EntityNotFoundException("Unable to get wallet with " + userId + " user Id, not found.");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }


    }

}
