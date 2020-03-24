package by.training.game;

import by.training.connection.ConnectionProvider;
import by.training.core.Bean;
import by.training.core.DaoException;
import by.training.core.EntityNotFoundException;
import by.training.player.PlainPlayerDto;
import by.training.tournament.TournamentDto;
import by.training.util.DaoMapper;
import by.training.util.DateConverter;
import by.training.util.EntityDtoConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Bean
public class GameDaoImpl implements GameDao {

    private static final String INSERT =
            "INSERT INTO game (bracket_index, start_time, end_time, first_player_id, second_player_id, tournament_id) " +
                    "VALUES (?,?,?,?,?,?)";

    private static final String SELECT =
            "SELECT game.game_id, bracket_index, start_time, end_time, first_player_id, second_player_id, tournament_id, gameserver.game_server_id " +
                    "FROM game " +
                    "LEFT JOIN gameserver " +
                    "ON game.game_id = gameserver.game_id " +
                    "WHERE game.game_id = ?";

    private static final String UPDATE =
            "UPDATE game " +
                    "SET bracket_index=?, start_time=?, end_time=?, first_player_id=?, second_player_id=?, tournament_id=? " +
                    "WHERE game_id = ?";

    private static final String DELETE =
            "DELETE FROM game " +
                    "WHERE game_id = ?";

    private static final String SELECT_ALL =
            "SELECT game.game_id, bracket_index, start_time, end_time, first_player_id, second_player_id, tournament_id , gameserver.game_server_id " +
                    "FROM game " +
                    "LEFT JOIN gameserver " +
                    "ON game.game_id = gameserver.game_id";

    private static final String INSERT_NEW =
            "INSERT INTO game (bracket_index, tournament_id) " +
                    "VALUES (?,?)";

    private static final String UPDATE_FIRST =
            "UPDATE game " +
                    "SET first_player_id=? " +
                    "WHERE game_id = ?";

    private static final String UPDATE_SECOND =
            "UPDATE game " +
                    "SET second_player_id=? " +
                    "WHERE game_id = ?";

    private static final String UPDATE_START_TIME =
            "UPDATE game " +
                    "SET start_time = ? " +
                    "WHERE game_id = ?";

    private static final String SELECT_BY_BRACKET_INDEX_OF_TOURNAMENT =
            "SELECT game.game_id, bracket_index, start_time, end_time, first_player_id, second_player_id, tournament_id, gameserver.game_server_id " +
                    "FROM game " +
                    "LEFT JOIN gameserver " +
                    "ON game.game_id = gameserver.game_id " +
                    "WHERE game.bracket_index = ? " +
                    "AND game.tournament_id=?";

    private static final String SELECT_ALL_OF_PLAYER =
            "SELECT game.game_id, bracket_index, start_time, end_time, first_player_id, second_player_id, tournament_id , gameserver.game_server_id " +
                    "FROM game " +
                    "LEFT JOIN gameserver " +
                    "ON game.game_id = gameserver.game_id " +
                    "WHERE game.first_player_id = ? " +
                    "OR game.second_player_id = ?";

    private static final String SELECT_ALL_OF_TOURNAMENT =
            "SELECT game.game_id, bracket_index, start_time, end_time, first_player_id, second_player_id, tournament_id , gameserver.game_server_id " +
                    "FROM game " +
                    "LEFT JOIN gameserver " +
                    "ON game.game_id = gameserver.game_id " +
                    "WHERE game.tournament_id = ?";

    private static final String SELECT_FIRST =
            "SELECT player_id, player_name, player_surname, player_nickname, player_photo, total_won, user_account_id " +
                    "FROM player " +
                    "INNER JOIN game g " +
                    "ON player.player_id = g.first_player_id " +
                    "WHERE g.game_id = ?";

    private static final String SELECT_SECOND =
            "SELECT player_id, player_name, player_surname, player_nickname, player_photo, total_won, user_account_id " +
                    "FROM player " +
                    "INNER JOIN game g " +
                    "ON player.player_id = g.second_player_id " +
                    "WHERE g.game_id = ?";

    private static final String SELECT_SERVER_OF_GAME =
            "SELECT game_server_id, points_to_win, player_one_points, player_two_points, server_path, game_id " +
                    "FROM gameserver " +
                    "WHERE game_id = ?";

    private static final String SELECT_TOURNAMENT_OF_GAME =
            "SELECT tournament.tournament_id, tournament_name, tournament_logo, prize_pool, organizer_reward_rate, buy_in, participants_number, tournament_status, organizer_id " +
                    "FROM tournament " +
                    "RIGHT JOIN game g " +
                    "ON tournament.tournament_id = g.tournament_id " +
                    "WHERE g.game_id = ?";


    private static final Logger LOGGER = LogManager.getLogger(GameDaoImpl.class);
    private final ConnectionProvider provider;


    public GameDaoImpl(ConnectionProvider provider) {
        this.provider = provider;
    }


    @Override
    public long save(PlainGameDto plainGameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            Game game = EntityDtoConverter.fromDto(plainGameDto);
            fillSaveStatement(statement, game);
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
    public PlainGameDto get(long id) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return DaoMapper.mapGameDto(resultSet);
                } else {
                    LOGGER.error("Unable to get game with " + id + " id, not found.");
                    throw new EntityNotFoundException("Unable to get game with " + id + " id, not found.");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    @Override
    public boolean update(PlainGameDto plainGameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            Game game = EntityDtoConverter.fromDto(plainGameDto);
            fillUpdateStatement(statement, game);
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
    public List<PlainGameDto> findAll() throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            List<PlainGameDto> result = new ArrayList<>();

            while (resultSet.next()) {
                PlainGameDto plainGameDto = DaoMapper.mapGameDto(resultSet);
                result.add(plainGameDto);
            }

            return result;

        } catch (SQLException e) {
            LOGGER.error("Unable to perform all entities retrieving.", e);
            throw new DaoException("Unable to perform all entities retrieving.", e);
        }
    }


    @Override
    public long saveNew(PlainGameDto plainGameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW, Statement.RETURN_GENERATED_KEYS)) {

            Game game = EntityDtoConverter.fromDto(plainGameDto);

            fillSaveNewStatement(statement, game);
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
    public ComplexGameDto getComplex(long id) throws DaoException {
        PlainGameDto plain = get(id);
        return upgrade(plain);
    }


    @Override
    public ComplexGameDto getComplexByBracketIndex(int gameIndex, long tournamentId) throws DaoException {
        PlainGameDto plain = getByBracketIndex(gameIndex, tournamentId);
        return upgrade(plain);
    }


    @Override
    public void updateFirstPlayerId(PlainGameDto gameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FIRST)) {

            Game game = EntityDtoConverter.fromDto(gameDto);

            int i = 0;
            statement.setLong(++i, game.getFirstPlayerId());
            statement.setLong(++i, game.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity updating.", e);
            throw new DaoException("Unable to perform entity updating.", e);
        }
    }


    @Override
    public void updateSecondPlayerId(PlainGameDto gameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SECOND)) {

            Game game = EntityDtoConverter.fromDto(gameDto);

            int i = 0;
            statement.setLong(++i, game.getSecondPlayerId());
            statement.setLong(++i, game.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity updating.", e);
            throw new DaoException("Unable to perform entity updating.", e);
        }
    }

    @Override
    public void resetFirstPlayerId(PlainGameDto gameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FIRST)) {

            Game game = EntityDtoConverter.fromDto(gameDto);

            int i = 0;
            statement.setNull(++i, Types.INTEGER);
            statement.setLong(++i, game.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity updating.", e);
            throw new DaoException("Unable to perform entity updating.", e);
        }
    }

    @Override
    public void resetSecondPlayerId(PlainGameDto gameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SECOND)) {

            Game game = EntityDtoConverter.fromDto(gameDto);

            int i = 0;
            statement.setNull(++i, Types.INTEGER);
            statement.setLong(++i, game.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity updating.", e);
            throw new DaoException("Unable to perform entity updating.", e);
        }
    }


    @Override
    public void updateStartTime(PlainGameDto gameDto) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_START_TIME)) {

            Game game = EntityDtoConverter.fromDto(gameDto);

            int i = 0;
            Timestamp timestamp = DateConverter.toTimeStamp(game.getStartTime());
            statement.setTimestamp(++i, timestamp);
            statement.setLong(++i, game.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity updating.", e);
            throw new DaoException("Unable to perform entity updating.", e);
        }
    }


    @Override
    public List<ComplexGameDto> findAllComplex() throws DaoException {
        List<PlainGameDto> plain = findAll();
        return upgrade(plain);
    }


    @Override
    public List<ComplexGameDto> findAllComplexOfPlayer(long playerId) throws DaoException {
        List<PlainGameDto> plain = findAllOfPlayer(playerId);
        return upgrade(plain);
    }


    @Override
    public List<ComplexGameDto> findAllComplexOfTournament(long tournamentId) throws DaoException {
        List<PlainGameDto> plain = findAllOfTournament(tournamentId);
        return upgrade(plain);
    }


    private PlainGameDto getByBracketIndex(int gameIndex, long tournamentId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_BRACKET_INDEX_OF_TOURNAMENT)) {

            int i = 0;
            statement.setLong(++i, gameIndex);
            statement.setLong(++i, tournamentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return DaoMapper.mapGameDto(resultSet);
                } else {
                    LOGGER.error("Unable to get game with " + gameIndex + " game index, not found.");
                    throw new EntityNotFoundException("Unable to get game with "
                            + gameIndex + " game index, not found.");
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private List<PlainGameDto> findAllOfPlayer(long playerId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_OF_PLAYER)) {

            int i = 0;
            statement.setLong(++i, playerId);
            statement.setLong(++i, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<PlainGameDto> result = new ArrayList<>();

                while (resultSet.next()) {
                    PlainGameDto game = DaoMapper.mapGameDto(resultSet);
                    result.add(game);
                }

                return result;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private List<PlainGameDto> findAllOfTournament(long tournamentId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_OF_TOURNAMENT)) {

            int i = 0;
            statement.setLong(++i, tournamentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<PlainGameDto> result = new ArrayList<>();

                while (resultSet.next()) {
                    PlainGameDto game = DaoMapper.mapGameDto(resultSet);
                    result.add(game);
                }

                return result;
            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private void fillSaveStatement(PreparedStatement statement, Game game) throws SQLException {
        int i = 0;
        statement.setInt(++i, game.getBracketIndex());
        java.sql.Timestamp sqlStartTime = DateConverter.toTimeStamp(game.getStartTime());
        statement.setTimestamp(++i, sqlStartTime);
        java.sql.Timestamp sqlEndTime = DateConverter.toTimeStamp(game.getEndTime());
        statement.setTimestamp(++i, sqlEndTime);
        statement.setLong(++i, game.getFirstPlayerId());
        statement.setLong(++i, game.getSecondPlayerId());
        statement.setLong(++i, game.getTournamentId());
    }


    private void fillSaveNewStatement(PreparedStatement statement, Game game) throws SQLException {
        int i = 0;
        statement.setInt(++i, game.getBracketIndex());
        statement.setLong(++i, game.getTournamentId());
    }


    private void fillUpdateStatement(PreparedStatement statement, Game game) throws SQLException {
        int i = 0;
        statement.setInt(++i, game.getBracketIndex());
        java.sql.Timestamp sqlStartTime = DateConverter.toTimeStamp(game.getStartTime());
        statement.setTimestamp(++i, sqlStartTime);
        java.sql.Timestamp sqlEndTime = DateConverter.toTimeStamp(game.getEndTime());
        statement.setTimestamp(++i, sqlEndTime);
        statement.setLong(++i, game.getFirstPlayerId());
        statement.setLong(++i, game.getSecondPlayerId());
        statement.setLong(++i, game.getTournamentId());
        statement.setLong(++i, game.getId());
    }


    private List<ComplexGameDto> upgrade(List<PlainGameDto> plain) throws DaoException {

        List<ComplexGameDto> complex = new ArrayList<>();

        for (PlainGameDto game : plain) {
            complex.add(upgrade(game));
        }

        return complex;
    }


    private ComplexGameDto upgrade(PlainGameDto plainGame) throws DaoException {

        long gameId = plainGame.getId();

        GameServerDto server = getServerOfGame(gameId);
        TournamentDto tournamentDto = getGameTournament(gameId);

        ComplexGameDto game = new ComplexGameDto();
        game.setId(gameId);

        try {
            PlainPlayerDto playerOne = getFirstPlayer(gameId);
            game.setFirstPlayer(playerOne);

            PlainPlayerDto playerTwo = getSecondPlayer(gameId);
            game.setSecondPlayer(playerTwo);

        } catch (EntityNotFoundException e) {
            LOGGER.info("No player(s) for game id#" + gameId);
        }

        game.setGameServer(server);
        game.setTournament(tournamentDto);
        game.setBracketIndex(plainGame.bracketIndex);
        game.setStartTime(plainGame.startTime);
        game.setEndTime(plainGame.endTime);

        return game;
    }


    private PlainPlayerDto getFirstPlayer(long gameId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_FIRST)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return DaoMapper.mapPlayerDto(resultSet);
                } else {
                    LOGGER.error("Unable to get first player with " + gameId + " game id, not found.");
                    throw new EntityNotFoundException("Unable to get first player with "
                            + gameId + " game id, not found.");
                }

            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private PlainPlayerDto getSecondPlayer(long gameId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SECOND)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return DaoMapper.mapPlayerDto(resultSet);
                } else {
                    LOGGER.error("Unable to get second player with " + gameId + " game id, not found.");
                    throw new EntityNotFoundException("Unable to get second player with "
                            + gameId + " game id, not found.");
                }

            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private GameServerDto getServerOfGame(long gameId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SERVER_OF_GAME)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return DaoMapper.mapGameServerDto(resultSet);
                } else {
                    LOGGER.error("Unable to get game server with " + gameId + " game id, not found.");
                    throw new EntityNotFoundException("Unable to get game server with "
                            + gameId + " game id, not found.");
                }

            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }


    private TournamentDto getGameTournament(long gameId) throws DaoException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TOURNAMENT_OF_GAME)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return DaoMapper.mapTournamentDto(resultSet);
                } else {
                    LOGGER.error("Unable to get tournament with " + gameId + " game id, not found.");
                    throw new DaoException("Unable to get tournament with "
                            + gameId + " game id, not found.");
                }

            }

        } catch (SQLException e) {
            LOGGER.error("Unable to perform entity retrieving.", e);
            throw new DaoException("Unable to perform entity retrieving.", e);
        }
    }

}
