package by.training.user;

import by.training.core.Bean;
import by.training.connection.ConnectionProvider;
import by.training.common.DaoException;
import by.training.util.EntityConverter;
import by.training.util.DaoMapper;
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
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.balance, w.currency, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE user_account.user_id=?";
    private static final String SELECT_BY_USERNAME =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.balance, w.currency, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE username=?";
    private static final String SELECT_BY_EMAIL =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.balance, w.currency, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE user_email=?";
    private static final String SELECT_BY_USERNAME_AND_PASSWORD =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.balance, w.currency, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE username=? AND user_password=?";
    private static final String UPDATE =
            "UPDATE user_account " +
                    "SET account_avatar=?, username=?, user_password=?, password_key=?, user_email=?, user_type=?, lang=? " +
                    "WHERE user_id = ?";
    private static final String DELETE =
            "DELETE FROM user_account " +
                    "WHERE user_id = ?";
    private static final String SELECT_ALL =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, lang, w.balance, w.currency, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "INNER JOIN wallet w " +
                    "ON user_account.user_id = w.user_id " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private ConnectionProvider provider;

    public UserDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public long save(UserDto userDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            User entity = EntityConverter.fromDto(userDto);
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
                    return DaoMapper.mapUserDto(resultSet);
                } else  {
                    LOGGER.error("Unable to get user with " + id + " id, not found.");
                    throw new DaoException("Unable to get user with " + id + " id not found.");
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

            User entity = EntityConverter.fromDto(userDto);
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
                UserDto userDto = DaoMapper.mapUserDto(resultSet);
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
                    userDto = DaoMapper.mapUserDto(resultSet);
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
                    return DaoMapper.mapUserDto(resultSet);
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
                    userDto = DaoMapper.mapUserDto(resultSet);
                }
                return userDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform user search.", e);
            throw new DaoException("Unable to perform user search.", e);
        }
    }

    @Override
    public boolean updatePlayerId(UserDto userDto) throws DaoException {
        return false;
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

}
