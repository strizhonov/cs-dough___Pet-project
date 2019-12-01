package by.training.dao;

import by.training.appentry.Bean;
import by.training.connection.ConnectionProvider;
import by.training.dto.UserDto;
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
            "INSERT INTO user_account (account_avatar, username, user_password, password_key, user_email, user_type, language, wallet_id) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
    private static final String SELECT =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, language, wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE user_account.user_id=?";
    private static final String SELECT_BY_USERNAME =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, language, wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE username=?";
    private static final String SELECT_BY_EMAIL =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_type, language  wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE user_email=?";
    private static final String SELECT_BY_USERNAME_AND_PASSWORD =
            "SELECT user_account.user_id, account_avatar, password_key, user_email, user_type, language, wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id " +
                    "WHERE username=? AND user_password=?";
    private static final String UPDATE =
            "UPDATE user_account " +
                    "SET account_avatar=?, username=?, user_password=?, password_key=?, user_email=?, user_type=?, language=?, wallet_id=? " +
                    "WHERE user_id = ?";
    private static final String DELETE =
            "DELETE FROM user_account " +
                    "WHERE user_id = ?";
    private static final String SELECT_ALL_FOR =
            "SELECT user_account.user_id, account_avatar, username, user_password, password_key, user_email, user_type, language, wallet_id, p.player_id, o.organizer_id " +
                    "FROM user_account " +
                    "LEFT JOIN player p " +
                    "ON user_account.user_id = p.user_account_id " +
                    "LEFT JOIN organizer o " +
                    "ON user_account.user_id = o.user_account_id";

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private ConnectionProvider provider;
    private DaoMapper mapper;

    public UserDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
        this.mapper = new DaoMapper();
    }

    @Override
    public long save(UserDto userDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            fillSaveStatement(statement, userDto);
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
                UserDto userDto = null;
                if (resultSet.next()) {
                    userDto = mapper.mapUserDto(resultSet);
                }
                if (userDto == null) {
                    LOGGER.error("Unable to get user with " + id + " id, not found.");
                    throw new DaoException("Unable to get user with " + id + " id not found.");
                }
                return userDto;
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

            fillUpdateStatement(statement, userDto);
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
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FOR);
             ResultSet resultSet = statement.executeQuery()) {

            List<UserDto> result = new ArrayList<>();
            if (resultSet.next()) {
                UserDto userDto = mapper.mapUserDto(resultSet);
                result.add(userDto);
            }
            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    @Override
    public UserDto login(String login, String password) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME_AND_PASSWORD)) {

            int i = 0;
            statement.setString(++i, login);
            statement.setString(++i, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                UserDto userDto = null;
                if (resultSet.next()) {
                    userDto = mapper.mapUserDto(resultSet);
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
                UserDto userDto = null;
                if (resultSet.next()) {
                    userDto = mapper.mapUserDto(resultSet);
                }
                return userDto;
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
                    userDto = mapper.mapUserDto(resultSet);
                }
                return userDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform user search.", e);
            throw new DaoException("Unable to perform user search.", e);
        }
    }

    private void fillSaveStatement(PreparedStatement statement, UserDto user) throws SQLException, IOException {
        int i = 0;
        byte[] avatar = user.getAvatar();
        InputStream stream = new ByteArrayInputStream(avatar);
        statement.setBlob(++i, stream);
        stream.close();
        statement.setString(++i, user.getUsername());
        statement.setString(++i, user.getPassword());
        statement.setString(++i, user.getPasswordKey());
        statement.setString(++i, user.getEmail());
        statement.setString(++i, user.getType().name());
        statement.setString(++i, user.getLanguage().name());
        statement.setLong(++i, user.getWalletId());
    }


    private void fillUpdateStatement(PreparedStatement statement, UserDto user) throws SQLException, IOException {
        int i = 0;
        byte[] avatar = user.getAvatar();
        InputStream stream = new ByteArrayInputStream(avatar);
        statement.setBlob(++i, stream);
        stream.close();
        statement.setString(++i, user.getUsername());
        statement.setString(++i, user.getPassword());
        statement.setString(++i, user.getPasswordKey());
        statement.setString(++i, user.getEmail());
        statement.setString(++i, user.getType().name());
        statement.setString(++i, user.getLanguage().name());
        statement.setLong(++i, user.getWalletId());
        statement.setLong(++i, user.getId());
    }

}
