package by.training.util;

import by.training.game.Game;
import by.training.game.GameServer;
import by.training.game.GameServerDto;
import by.training.game.PlainGameDto;
import by.training.organizer.Organizer;
import by.training.organizer.OrganizerDto;
import by.training.player.Player;
import by.training.player.PlayerDto;
import by.training.tournament.Tournament;
import by.training.tournament.TournamentDto;
import by.training.user.User;
import by.training.user.UserDto;
import by.training.user.Wallet;
import by.training.user.WalletDto;

public class EntityDtoConverter {


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


    public static Game fromDto(PlainGameDto plainGameDto) {
        return new Game.Builder()
                .id(plainGameDto.getId())
                .bracketIndex(plainGameDto.getBracketIndex())
                .startTime(plainGameDto.getStartTime())
                .endTime(plainGameDto.getEndTime())
                .firstPlayerId(plainGameDto.getFirstPlayerId())
                .secondPlayerId(plainGameDto.getSecondPlayerId())
                .tournamentId(plainGameDto.getTournamentId())
                .build();
    }


    public static GameServer fromDto(GameServerDto gameServerDto) {
        return new GameServer.Builder()
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
                .buyIn(tournamentDto.getBuyIn())
                .participantsNumber(tournamentDto.getPlayersNumber())
                .status(tournamentDto.getStatus())
                .organizerId(tournamentDto.getOrganizerId())
                .build();
    }


}
