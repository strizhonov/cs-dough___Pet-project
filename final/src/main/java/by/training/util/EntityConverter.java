package by.training.util;

import by.training.game.Game;
import by.training.game.GameDto;
import by.training.game.GameServerDto;
import by.training.game.GameServerEntity;
import by.training.tournament.Tournament;
import by.training.tournament.TournamentDto;
import by.training.user.*;

public class EntityConverter {

    public static User fromDto(UserDto userDto) {
        return new User.Builder()
                .id(userDto.getId())
                .avatar(userDto.getAvatar())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .passwordKey(userDto.getPasswordKey())
                .email(userDto.getEmail())
                .type(userDto.getType())
                .language(userDto.getLanguage())
                .build();
    }

    public static Wallet fromDto(WalletDto walletDto) {
        return new Wallet.Builder()
                .id(walletDto.getId())
                .balance(walletDto.getBalance())
                .currency(walletDto.getCurrency())
                .userId(walletDto.getUserId())
                .build();
    }

    public static Game fromDto(GameDto gameDto) {
        return new Game.Builder()
                .id(gameDto.getId())
                .bracketIndex(gameDto.getBracketIndex())
                .startTime(gameDto.getStartTime())
                .endTime(gameDto.getEndTime())
                .firstPlayerId(gameDto.getFirstPlayerId())
                .secondPlayerId(gameDto.getSecondPlayerId())
                .winnerId(gameDto.getWinnerId())
                .tournamentId(gameDto.getTournamentId())
                .build();
    }

    public static GameServerEntity fromDto(GameServerDto gameServerDto) {
        return new GameServerEntity.Builder()
                .id(gameServerDto.getId())
                .pointsToWin(gameServerDto.getPointsToWin())
                .playerOnePoints(gameServerDto.getPlayerOnePoints())
                .playerTwoPoints(gameServerDto.getPlayerTwoPoints())
                .path(gameServerDto.getPath())
                .gameId(gameServerDto.getGameId())
                .build();
    }

    public static Organizer fromDto(OrganizerDto organizerDto) {
        return new Organizer.Builder()
                .id(organizerDto.getId())
                .name(organizerDto.getName())
                .logo(organizerDto.getLogo())
                .userId(organizerDto.getUserId())
                .build();
    }

    public static Player fromDto(PlayerDto playerDto) {
        return new Player.Builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .surname(playerDto.getSurname())
                .nickname(playerDto.getNickname())
                .photo(playerDto.getPhoto())
                .country(playerDto.getCountry())
                .totalWon(playerDto.getTotalWon())
                .userId(playerDto.getUserId())
                .build();
    }

    public static Tournament fromDto(TournamentDto tournamentDto) {
        return new Tournament.Builder()
                .id(tournamentDto.getId())
                .name(tournamentDto.getName())
                .logo(tournamentDto.getLogo())
                .prizePool(tournamentDto.getPrizePool())
                .buiIn(tournamentDto.getBuyIn())
                .playersNumber(tournamentDto.getPlayersNumber())
                .startDate(tournamentDto.getStartDate())
                .endDate(tournamentDto.getEndDate())
                .status(tournamentDto.getStatus())
                .organizerId(tournamentDto.getOrganizerId())
                .build();
    }

}
