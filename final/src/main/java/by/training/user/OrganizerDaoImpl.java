package by.training.user;

import by.training.core.Bean;
import by.training.connection.ConnectionProvider;
import by.training.common.DaoException;
import by.training.util.DaoMapper;
import by.training.util.EntityConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean
public class OrganizerDaoImpl implements OrganizerDao {

    private static final String INSERT =
            "INSERT INTO organizer (organizer_name, organizer_logo, user_account_id) " +
                    "VALUES (?,?,?)";
    private static final String SELECT =
            "SELECT organizer_id, organizer_name, organizer_logo, user_account_id " +
                    "FROM organizer " +
                    "WHERE organizer_id = ?";
    private static final String SELECT_BY_NAME =
            "SELECT organizer_id, organizer_name, organizer_logo, user_account_id " +
                    "FROM organizer " +
                    "WHERE organizer_name = ?";
    private static final String UPDATE =
            "UPDATE organizer " +
                    "SET organizer_name = ?, organizer_logo = ?, user_account_id = ? " +
                    "WHERE organizer_id = ?";
    private static final String DELETE =
            "DELETE FROM organizer " +
                    "WHERE organizer_id = ?";
    private static final String SELECT_ALL =
            "SELECT organizer_id, organizer_name, organizer_logo, user_account_id " +
                    "FROM organizer ";
    private static final String SELECT_ORGANIZER_TOURNAMENTS_IDS =
            "SELECT tournament_id " +
                    "FROM tournament " +
                    "WHERE organizer_id = ?";

    private static final Logger LOGGER = LogManager.getLogger(OrganizerDaoImpl.class);
    private ConnectionProvider provider;

    public OrganizerDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public long save(OrganizerDto organizerDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            Organizer organizer = EntityConverter.fromDto(organizerDto);
            fillSaveStatement(statement, organizer);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                return 0;
            }

        } catch (IOException | SQLException e) {
            LOGGER.error("Unable to perform all entity retrieving.", e);
            throw new DaoException("Unable to perform all entity retrieving.", e);
        }
    }

    @Override
    public OrganizerDto get(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return compile(resultSet);
                } else {
                    LOGGER.error("Unable to get organizer with " + id + " id, not found.");
                    throw new DaoException("Unable to get organizer with " + id + " id, not found.");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }

    @Override
    public boolean update(OrganizerDto organizerDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            Organizer organizer = EntityConverter.fromDto(organizerDto);
            fillUpdateStatement(statement, organizer);
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
    public List<OrganizerDto> findAll() throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            List<OrganizerDto> result = new ArrayList<>();
            while (resultSet.next()) {
                OrganizerDto organizerDto = compile(resultSet);
                result.add(organizerDto);
            }
            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    @Override
    public OrganizerDto findByName(String name) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                OrganizerDto organizerDto = null;
                if (resultSet.next()) {
                    organizerDto = compile(resultSet);
                }
                return organizerDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }

    private List<Long> findAllTournamentsIdsByOrganizerId(long organizerId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORGANIZER_TOURNAMENTS_IDS)) {

            statement.setLong(1, organizerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> result = new ArrayList<>();
                while (resultSet.next()) {
                    long tournamentId = resultSet.getLong(DaoMapper.Column.TOURNAMENT_ID.toString());
                    result.add(tournamentId);
                }
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    private void fillSaveStatement(PreparedStatement statement, Organizer organizer) throws IOException, SQLException {
        int i = 0;
        statement.setString(++i, organizer.getName());
        try (InputStream stream = new ByteArrayInputStream(organizer.getLogo())) {
            statement.setBlob(++i, stream);
            statement.setLong(++i, organizer.getUserId());
        }
    }

    private void fillUpdateStatement(PreparedStatement statement, Organizer organizer) throws SQLException, IOException {
        int i = 0;
        statement.setString(++i, organizer.getName());
        try (InputStream stream = new ByteArrayInputStream(organizer.getLogo())) {
            statement.setBlob(++i, stream);
            statement.setLong(++i, organizer.getUserId());
            statement.setLong(++i, organizer.getId());
        }
    }

    private OrganizerDto compile(ResultSet resultSet) throws SQLException, DaoException {
        OrganizerDto organizerDto = DaoMapper.mapOrganizerDto(resultSet);
        List<Long> tournamentsId = findAllTournamentsIdsByOrganizerId(organizerDto.getId());
        organizerDto.setTournamentsIdList(tournamentsId);
        return organizerDto;
    }

}
