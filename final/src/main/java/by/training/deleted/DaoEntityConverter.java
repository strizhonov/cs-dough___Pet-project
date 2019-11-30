package by.training.deleted;

import by.training.dto.*;
import by.training.entity.*;

public class DaoEntityConverter {

    Game gameFromDto(GameDto gameDto) {
        Game game = new Game();
        game.setId(gameDto.getId());
        game.setBracketIndex(gameDto.getBracketIndex());
        game.setStartTime(gameDto.getStartTime());
        game.setEndTime(gameDto.getEndTime());
        game.setType(gameDto.getType());
        game.setFirstPlayerId(gameDto.getFirstPlayerId());
        game.setSecondPlayerId(gameDto.getSecondPlayerId());
        game.setWinnerId(gameDto.getWinnerId());
        game.setTournamentId(gameDto.getTournamentId());
        return game;
    }

    Organizer organizerFromDto(OrganizerDto organizerDto) {
        Organizer organizer = new Organizer();
        organizer.setId(organizerDto.getId());
        organizer.setLogo(organizerDto.getLogo());
        organizer.setName(organizerDto.getName());
        organizer.setUserId(organizerDto.getUserId());
        return organizer;
    }

    GameServerEntity gameServerFromDto(GameServerDto gameServerDto) {
        GameServerEntity entity = new GameServerEntity();
        entity.setId(gameServerDto.getId());
        entity.setPointsToWin(gameServerDto.getPointsToWin());
        entity.setPlayerOnePoints(gameServerDto.getPlayerOnePoints());
        entity.setPlayerTwoPoints(gameServerDto.getPlayerTwoPoints());
        entity.setPath(gameServerDto.getPath());
        entity.setGameId(gameServerDto.getGameId());
        return entity;
    }

    Player playerFromDto(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setName(playerDto.getName());
        player.setSurname(playerDto.getSurname());
        player.setNickname(playerDto.getNickname());
        player.setPhoto(playerDto.getPhoto());
        player.setTotalWon(playerDto.getTotalWon());
        player.setCountry(playerDto.getCountry());
        player.setUserId(playerDto.getUserId());
        return player;
    }

    Tournament tournamentFromDto(TournamentDto tournamentDto) {
        Tournament tournament = new Tournament();
        tournament.setId(tournamentDto.getId());
        tournament.setName(tournamentDto.getName());
        tournament.setLogo(tournamentDto.getLogo());
        tournament.setPrizePool(tournamentDto.getPrizePool());
        tournament.setBuiIn(tournamentDto.getBuiIn());
        tournament.setPlayersNumber(tournamentDto.getPlayersNumber());
        tournament.setStartDate(tournamentDto.getStartDate());
        tournament.setEndDate(tournamentDto.getEndDate());
        tournament.setStatus(tournamentDto.getStatus());
        tournament.setOrganizerId(tournamentDto.getOrganizerId());
        return tournament;
    }

    TournamentDto dtoFromTournament(Tournament tournament) {
        TournamentDto tournamentDto = new TournamentDto();
        tournamentDto.setId(tournament.getId());
        tournamentDto.setName(tournament.getName());
        tournamentDto.setLogo(tournament.getLogo());
        tournamentDto.setPrizePool(tournament.getPrizePool());
        tournamentDto.setBuiIn(tournament.getBuiIn());
        tournamentDto.setPlayersNumber(tournament.getPlayersNumber());
        tournamentDto.setStartDate(tournament.getStartDate());
        tournamentDto.setEndDate(tournament.getEndDate());
        tournamentDto.setStatus(tournament.getStatus());
        tournamentDto.setOrganizerId(tournament.getOrganizerId());
        return tournamentDto;
    }

    User userFromDto(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setAvatar(userDto.getAvatar());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPasswordKey(userDto.getPasswordKey());
        user.setEmail(userDto.getEmail());
        user.setType(userDto.getType());
        user.setLanguage(userDto.getLanguage());
        user.setWalletId(userDto.getWalletId());
        return user;
    }

    Wallet walletFromDto(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setId(walletDto.getId());
        wallet.setBalance(walletDto.getBalance());
        wallet.setCurrency(walletDto.getCurrency());
        return wallet;
    }

}
