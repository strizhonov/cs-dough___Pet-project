package by.training.dao;

import by.training.appentry.Bean;
import by.training.connection.ConnectionProvider;
import by.training.dao.util.DaoMapper;
import by.training.dao.util.EntityConverter;
import by.training.dto.TournamentJoiningDto;
import by.training.dto.PlayerDto;
import by.training.entity.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean
public class PlayerDaoImpl implements PlayerDao {

    private static final String INSERT =
            "INSERT INTO player (player_name, player_surname, player_nickname, player_photo, player_country, total_won, user_account_id) " +
                    "VALUES (?,?,?,?,?,?,?)";
    private static final String PARTICIPANT_INSERT =
            "INSERT INTO participant (player_id, tournament_id) " +
                    "VALUES (?,?) ";
    private static final String SELECT =
            "SELECT player_id, player_name, player_surname, player_nickname, player_photo, player_country, total_won, user_account_id " +
                    "FROM player " +
                    "WHERE player.player_id = ?";
    private static final String UPDATE =
            "UPDATE player " +
                    "SET player_name=?, player_surname=?, player_nickname=?, player_photo=?, player_country=? + total_won=? + user_account_id=? " +
                    "WHERE player_id = ?";
    private static final String DELETE =
            "DELETE FROM player " +
                    "WHERE player_id = ?";
    private static final String SELECT_ALL =
            "SELECT player.player_id, player_name, player_surname, player_nickname, player_photo, player_country, total_won, user_account_id " +
                    "FROM player " +
                    "WHERE player.player_id = ?";
    private static final String SELECT_BY_NICKNAME =
            "SELECT player.player_id, player_name, player_surname, player_photo, player_country, total_won, user_account_id " +
                    "FROM player " +
                    "WHERE player.player_nickname = ?";
    private static final String SELECT_PLAYER_GAMES_IDS =
            "SELECT game_id " +
                    "FROM game " +
                    "WHERE first_player_id = ? OR second_player_id = ?";
    private static final String SELECT_PLAYER_TOURNAMENTS_IDS =
            "SELECT tournament_id " +
                    "FROM participant " +
                    "WHERE player_id = ?";

    private static final Logger LOGGER = LogManager.getLogger(PlayerDaoImpl.class);
    private ConnectionProvider provider;

    public PlayerDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public long save(PlayerDto playerDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            Player player = EntityConverter.fromDto(playerDto);
            fillSaveStatement(statement, player);
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
    public PlayerDto get(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return compile(resultSet);
                } else  {
                    LOGGER.error("Unable to get player with " + id + " id, not found.");
                    throw new DaoException("Unable to get player with " + id + " id, not found.");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }

    @Override
    public boolean update(PlayerDto playerDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            Player player = EntityConverter.fromDto(playerDto);
            fillUpdateStatement(statement, player);
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
    public List<PlayerDto> findAll() throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            List<PlayerDto> result = new ArrayList<>();
            while (resultSet.next()) {
                PlayerDto playerDto = compile(resultSet);
                result.add(playerDto);
            }
            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    @Override
    public PlayerDto findByNickname(String nickname) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NICKNAME)) {

            statement.setString(1, nickname);
            try (ResultSet resultSet = statement.executeQuery()) {
                PlayerDto playerDto = null;
                if (resultSet.next()) {
                    playerDto = compile(resultSet);
                }
                return playerDto;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity search.", e);
            throw new DaoException("Unable to perform entity search.", e);
        }
    }

    @Override
    public boolean addTournament(TournamentJoiningDto dto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(PARTICIPANT_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            int i = 0;
            statement.setLong(++i, dto.getPlayerId());
            statement.setLong(++i, dto.getTournamentId());
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity saving.", e);
            throw new DaoException("Unable to perform entity saving.", e);
        }
    }


    private List<Long> findGamesIdsByPlayerId(long playerId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PLAYER_GAMES_IDS)) {

            statement.setLong(1, playerId);
            statement.setLong(2, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> result = new ArrayList<>();
                while (resultSet.next()) {
                    long gameId = resultSet.getLong(DaoMapper.Column.GAME_ID.toString());
                    result.add(gameId);
                }
                return result;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    private List<Long> findTournamentsIdsByPlayerId(long playerId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PLAYER_TOURNAMENTS_IDS)) {

            statement.setLong(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Long> result = new ArrayList<>();
                while (resultSet.next()) {
                    long gameId = resultSet.getLong(DaoMapper.Column.TOURNAMENT_ID.toString());
                    result.add(gameId);
                }
                return result;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }

    private void fillSaveStatement(PreparedStatement statement, Player player) throws SQLException, IOException {
        int i = 0;
        statement.setString(++i, player.getName());
        statement.setString(++i, player.getSurname());
        statement.setString(++i, player.getNickname());
        try (InputStream stream = new ByteArrayInputStream(player.getPhoto());) {
            statement.setBlob(++i, stream);
            statement.setString(++i, player.getCountry());
            statement.setDouble(++i, player.getTotalWon());
            statement.setLong(++i, player.getUserId());
        }
    }

    private void fillUpdateStatement(PreparedStatement statement, Player player) throws SQLException, IOException {
        int i = 0;
        statement.setString(++i, player.getName());
        statement.setString(++i, player.getSurname());
        statement.setString(++i, player.getNickname());
        try (InputStream stream = new ByteArrayInputStream(player.getPhoto());) {
            statement.setBlob(++i, stream);
            statement.setString(++i, player.getCountry());
            statement.setDouble(++i, player.getTotalWon());
            statement.setLong(++i, player.getUserId());
            statement.setLong(++i, player.getId());
        }
    }

    private PlayerDto compile(ResultSet resultSet) throws SQLException, DaoException {
        PlayerDto playerDto = DaoMapper.mapPlayerDto(resultSet);
        List<Long> tournamentsIds = findTournamentsIdsByPlayerId(playerDto.getId());
        List<Long> gamesIds = findGamesIdsByPlayerId(playerDto.getId());
        playerDto.setGamesIds(gamesIds);
        playerDto.setTournamentsIds(tournamentsIds);
        return playerDto;
    }

}
