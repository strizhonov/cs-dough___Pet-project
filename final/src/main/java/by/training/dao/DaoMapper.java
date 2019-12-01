package by.training.dao;

import by.training.constant.ValuesContainer;
import by.training.dto.*;
import by.training.entity.Game;
import by.training.entity.Tournament;
import by.training.entity.User;
import by.training.entity.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.training.dao.DaoMapper.Column.*;

public class DaoMapper {

    enum Column {
        GAME_ID, BRACKET_INDEX, START_TIME, END_TIME, GAME_TYPE, FIRST_PLAYER_ID, SECOND_PLAYER_ID, WINNER_ID,
        TOURNAMENT_ID, GAME_SERVER_ID, POINTS_TO_WIN, PLAYER_ONE_POINTS, PLAYER_TWO_POINTS, SERVER_PATH,
        ORGANIZER_ID, ORGANIZER_NAME, ORGANIZER_LOGO, USER_ACCOUNT_ID, PARTICIPANTS_ID, PLAYER_ID, PLAYER_NAME,
        PLAYER_SURNAME, PLAYER_NICKNAME, PLAYER_PHOTO, PLAYER_COUNTRY, TOTAL_WON, TOURNAMENT_NAME, TOURNAMENT_LOGO,
        PRIZE_POOL, BUY_IN, PARTICIPANTS_NUMBER, START_DATE, END_DATE, TOURNAMENT_STATUS, USER_ID, ACCOUNT_AVATAR,
        USERNAME, USER_PASSWORD, PASSWORD_KEY, USER_EMAIL, USER_TYPE, LANGUAGE, WALLET_ID, CURRENCY, BALANCE;

        public String toString() {
            return this.name().toLowerCase();
        }

    }

    GameDto mapGameDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(GAME_ID.toString());
        int bracketIndex = rs.getInt(BRACKET_INDEX.toString());
        java.sql.Date sqlStartTime = rs.getDate(START_TIME.toString());
        java.util.Date startTime = sqlStartTime == null ? null : new java.util.Date(sqlStartTime.getTime());
        java.sql.Date sqlEndTime = rs.getDate(END_TIME.toString());
        java.util.Date endTime = sqlEndTime == null ? null : new java.util.Date(sqlEndTime.getTime());
        long firstPlayerId = rs.getLong(FIRST_PLAYER_ID.toString());
        long secondPlayerId = rs.getLong(SECOND_PLAYER_ID.toString());
        long winnerId = rs.getLong(WINNER_ID.toString());
        long tournamentId = rs.getLong(TOURNAMENT_ID.toString());
        long serverId = rs.getLong(GAME_SERVER_ID.toString());

        return GameDto.Builder.aGameDto()
                .id(id)
                .bracketIndex(bracketIndex)
                .startTime(startTime)
                .endTime(endTime)
                .firstPlayerId(firstPlayerId)
                .secondPlayerId(secondPlayerId)
                .winnerId(winnerId)
                .tournamentId(tournamentId)
                .gameServerId(serverId)
                .build();
    }

    OrganizerDto mapOrganizerDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(ORGANIZER_ID.toString());
        String name = rs.getString(ORGANIZER_NAME.toString());
        byte[] logo = rs.getBytes(ORGANIZER_LOGO.toString());
        long userId = rs.getLong(USER_ACCOUNT_ID.toString());

        return OrganizerDto.Builder.anOrganizerDto()
                .id(id)
                .name(name)
                .logo(logo)
                .userId(userId)
                .build();
    }

    GameServerDto mapGameServerDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(GAME_SERVER_ID.toString());
        int pointToWin = rs.getInt(POINTS_TO_WIN.toString());
        int firstPlayerPoints = rs.getInt(PLAYER_ONE_POINTS.toString());
        int secondPlayerPoints = rs.getInt(PLAYER_TWO_POINTS.toString());
        String path = rs.getString(SERVER_PATH.toString());
        long gameId = rs.getLong(GAME_ID.toString());

        return GameServerDto.Builder.aGameServerDto()
                .id(id)
                .pointsToWin(pointToWin)
                .playerOnePoints(firstPlayerPoints)
                .playerTwoPoints(secondPlayerPoints)
                .path(path)
                .gameId(gameId)
                .build();
    }

    PlayerDto mapPlayerDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(PLAYER_ID.toString());
        byte[] photo = rs.getBytes(PLAYER_PHOTO.toString());
        String name = rs.getString(PLAYER_NAME.toString());
        String surname = rs.getString(PLAYER_SURNAME.toString());
        String nickname = rs.getString(PLAYER_NICKNAME.toString());
        String country = rs.getString(PLAYER_COUNTRY.toString());
        double totalWon = rs.getDouble(TOTAL_WON.toString());
        long userId = rs.getLong(USER_ACCOUNT_ID.toString());

        return PlayerDto.Builder.aPlayerDto()
                .id(id)
                .photo(photo)
                .name(name)
                .surname(surname)
                .nickname(nickname)
                .country(country)
                .totalWon(totalWon)
                .userId(userId)
                .build();
    }

    UserDto mapUserDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(USER_ID.toString());
        byte[] avatar = rs.getBytes(ACCOUNT_AVATAR.toString());
        String username = rs.getString(USERNAME.toString());
        String password = rs.getString(USER_PASSWORD.toString());
        String passwordKey = rs.getString(PASSWORD_KEY.toString());
        String email = rs.getString(USER_EMAIL.toString());
        String sType = rs.getString(USER_TYPE.toString());
        User.UserType type = User.UserType.fromString(sType).orElse(ValuesContainer.DEFAULT_USER_TYPE);
        String sLanguage = rs.getString(LANGUAGE.toString());
        User.Language language = User.Language.fromLocaleString(sLanguage).orElse(ValuesContainer.DEFAULT_LANGUAGE);
        long walletId = rs.getLong(WALLET_ID.toString());
        long playerId = rs.getLong(PLAYER_ID.toString());
        long organizerId = rs.getLong(ORGANIZER_ID.toString());

        return UserDto.Builder.anUserDto()
                .id(id)
                .avatar(avatar)
                .username(username)
                .password(password)
                .passwordKey(passwordKey)
                .email(email)
                .type(type)
                .language(language)
                .walletId(walletId)
                .organizerId(organizerId)
                .playerAccountId(playerId)
                .build();
    }

    TournamentDto mapTournamentDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(TOURNAMENT_ID.toString());
        String name = rs.getString(TOURNAMENT_NAME.toString());
        byte[] logo = rs.getBytes(TOURNAMENT_LOGO.toString());
        double prizePool = rs.getDouble(PRIZE_POOL.toString());
        double buyIn = rs.getDouble(BUY_IN.toString());
        int participantsNumber = rs.getInt(PARTICIPANTS_NUMBER.toString());
        java.sql.Date sqlStartDate = rs.getDate(START_DATE.toString());
        java.util.Date utilStartDate = DaoUtil.toUtilDate(sqlStartDate);
        java.sql.Date sqlEndDate = rs.getDate(END_DATE.toString());
        java.util.Date utilEndDate = DaoUtil.toUtilDate(sqlEndDate);
        String sTournamentStatus = rs.getString(TOURNAMENT_STATUS.toString());
        Tournament.TournamentStatus tournamentStatus = Tournament.TournamentStatus
                .fromString(sTournamentStatus)
                .orElse(ValuesContainer.DEFAULT_TOURNAMENT_STATUS);
        long organizerId = rs.getLong(ORGANIZER_ID.toString());

        return TournamentDto.Builder.aTournamentDto()
                .id(id)
                .name(name)
                .logo(logo)
                .prizePool(prizePool)
                .buiIn(buyIn)
                .playersNumber(participantsNumber)
                .startDate(utilStartDate)
                .endDate(utilEndDate)
                .status(tournamentStatus)
                .organizerId(organizerId)
                .build();
    }

    WalletDto mapWalletDto(ResultSet rs) throws SQLException {
        long id = rs.getLong(WALLET_ID.toString());
        double balance = rs.getDouble(BALANCE.toString());
        String sCurrency = rs.getString(CURRENCY.toString());
        Wallet.CurrencyType currency = Wallet.CurrencyType
                .fromString(sCurrency)
                .orElse(ValuesContainer.DEFAULT_CURRENCY);
        long userId = rs.getLong(USER_ID.toString());

        return WalletDto.Builder.aWalletDto()
                .id(id)
                .currency(currency)
                .balance(balance)
                .userId(userId)
                .build();
    }

}
