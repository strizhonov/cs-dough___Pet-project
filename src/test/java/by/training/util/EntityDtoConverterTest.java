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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Date;

@RunWith(JUnit4.class)
public class EntityDtoConverterTest {


    @Test
    public void userFromDto() {
        UserDto userDto = new UserDto(0, null, "username", "password",
                "passwordKey", "email", null, null,
                null, 0, 0);

        User user = EntityDtoConverter.fromDto(userDto);

        Assert.assertTrue(
                user.getId() == userDto.getId()
                        && user.getAvatar() == null ? userDto.getAvatar() == null : Arrays.equals(user.getAvatar(), userDto.getAvatar())
                        && user.getUsername() == null ? userDto.getUsername() == null : user.getUsername().equals(userDto.getUsername())
                        && user.getPassword() == null ? userDto.getPassword() == null : user.getPassword().equals(userDto.getPassword())
                        && user.getPasswordKey() == null ? userDto.getPasswordKey() == null : user.getPasswordKey().equals(userDto.getPasswordKey())
                        && user.getEmail() == null ? userDto.getEmail() == null : user.getEmail().equals(userDto.getEmail())
                        && user.getType() == userDto.getType()
                        && user.getLanguage() == userDto.getLanguage()
        );
    }


    @Test
    public void walletFromDto() {
        WalletDto walletDto = new WalletDto(0, 0, null, 6);

        Wallet wallet = EntityDtoConverter.fromDto(walletDto);
        Assert.assertTrue(
                wallet.getId() == walletDto.getId()
                        && wallet.getBalance() == walletDto.getBalance()
                        && wallet.getCurrency() == walletDto.getCurrency()
                        && wallet.getUserId() == walletDto.getUserId()
        );
    }


    @Test
    public void gameFromDto() {
        PlainGameDto gameDto = new PlainGameDto(1, 5, null, new Date(), 6,
                3, 5, 7);

        Game game = EntityDtoConverter.fromDto(gameDto);

        Assert.assertTrue(
                game.getId() == gameDto.getId()
                        && game.getBracketIndex() == gameDto.getBracketIndex()
                        && game.getStartTime() == null ? gameDto.getStartTime() == null : game.getStartTime().equals(gameDto.getStartTime())
                        && game.getEndTime() == null ? gameDto.getEndTime() == null : game.getEndTime().equals(gameDto.getEndTime())
                        && game.getFirstPlayerId() == gameDto.getFirstPlayerId()
                        && game.getSecondPlayerId() == gameDto.getSecondPlayerId()
                        && game.getTournamentId() == gameDto.getTournamentId()
        );
    }


    @Test
    public void serverFromDto() {
        GameServerDto serverDto = new GameServerDto(0, 1, 2, 3, null, 5);

        GameServer server = EntityDtoConverter.fromDto(serverDto);

        Assert.assertTrue(
                server.getId() == serverDto.getId()
                        && server.getPointsToWin() == serverDto.getPointsToWin()
                        && server.getPlayerOnePoints() == serverDto.getPlayerOnePoints()
                        && server.getPlayerTwoPoints() == serverDto.getPlayerTwoPoints()
                        && server.getPath() == null ? serverDto.getPath() == null : server.getPath().equals(serverDto.getPath())
                        && server.getGameId() == serverDto.getGameId()

        );
    }


    @Test
    public void organizerFromDto() {
        OrganizerDto organizerDto = new OrganizerDto(1, null, null, 5, null);

        Organizer organizer = EntityDtoConverter.fromDto(organizerDto);

        Assert.assertTrue(
                organizer.getId() == organizerDto.getId()
                        && organizer.getLogo() == null ? organizerDto.getName() == null : Arrays.equals(organizer.getLogo(), organizerDto.getLogo())
                        && organizer.getName() == null ? organizerDto.getName() == null : organizer.getName().equals(organizerDto.getName())
                        && organizer.getUserId() == organizerDto.getUserId()
        );
    }


    @Test
    public void playerFromDto() {
        PlayerDto playerDto = new PlayerDto(44, null, "nickname", "surname", null, 0,
                0, null, null);

        Player player = EntityDtoConverter.fromDto(playerDto);

        Assert.assertTrue(
                player.getId() == playerDto.getId()
                        && player.getPhoto() == null ? playerDto.getPhoto() == null : Arrays.equals(player.getPhoto(), playerDto.getPhoto())
                        && player.getName() == null ? playerDto.getName() == null : player.getName().equals(playerDto.getName())
                        && player.getSurname() == null ? playerDto.getSurname() == null : player.getSurname().equals(playerDto.getSurname())
                        && player.getNickname() == null ? playerDto.getNickname() == null : player.getNickname().equals(playerDto.getNickname())
                        && player.getUserId() == playerDto.getUserId()
                        && player.getTotalWon() == playerDto.getTotalWon()
        );
    }


    @Test
    public void tournamentFromDto() {
        TournamentDto tournamentDto = new TournamentDto(0, null, null, 3, 4, 5, 6, null, 8, null, null);

        Tournament tournament = EntityDtoConverter.fromDto(tournamentDto);

        Assert.assertTrue(
                tournament.getId() == tournamentDto.getId()
                        && tournament.getLogo() == null ? tournamentDto.getLogo() == null : Arrays.equals(tournament.getLogo(), tournamentDto.getLogo())
                        && tournament.getName() == null ? tournamentDto.getName() == null : tournament.getName().equals(tournamentDto.getName())
                        && tournament.getPrizePool() == tournamentDto.getPrizePool()
                        && tournament.getOrganizerRewardPercentage() == tournamentDto.getOrganizerRewardPercentage()
                        && tournament.getBuyIn() == tournamentDto.getBuyIn()
                        && tournament.getParticipantsNumber() == tournamentDto.getPlayersNumber()
                        && tournament.getStatus() == tournamentDto.getStatus()
                        && tournament.getOrganizerId() == tournamentDto.getOrganizerId()
        );
    }

}
