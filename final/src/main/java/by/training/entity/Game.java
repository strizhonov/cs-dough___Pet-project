package by.training.entity;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public class Game extends Entity {

    private int bracketIndex;
    private Date startTime;
    private Date endTime;
    private GameType type = GameType.BO3;
    private long firstPlayerId;
    private long secondPlayerId;
    private long winnerId;
    private long tournamentId;

    public enum GameType {
        BO1, BO3;

        public static Optional<GameType> fromString(String type) {
            return Stream.of(GameType.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }
    }

    public Game() {
    }

    public int getBracketIndex() {
        return bracketIndex;
    }

    public void setBracketIndex(int bracketIndex) {
        this.bracketIndex = bracketIndex;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public long getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(long firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public long getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(long secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public static final class GameBuilder {
        private long id;
        private int bracketIndex;
        private Date startTime;
        private Date endTime;
        private GameType type = GameType.BO3;
        private long firstPlayerId;
        private long secondPlayerId;
        private long winnerId;
        private long tournamentId;

        private GameBuilder() {
        }

        public static GameBuilder aGame() {
            return new GameBuilder();
        }

        public GameBuilder id(long id) {
            this.id = id;
            return this;
        }

        public GameBuilder bracketIndex(int bracketIndex) {
            this.bracketIndex = bracketIndex;
            return this;
        }

        public GameBuilder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public GameBuilder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public GameBuilder type(GameType type) {
            this.type = type;
            return this;
        }

        public GameBuilder firstPlayerId(long firstPlayerId) {
            this.firstPlayerId = firstPlayerId;
            return this;
        }

        public GameBuilder secondPlayerId(long secondPlayerId) {
            this.secondPlayerId = secondPlayerId;
            return this;
        }

        public GameBuilder winnerId(long winnerId) {
            this.winnerId = winnerId;
            return this;
        }

        public GameBuilder tournamentId(long tournamentId) {
            this.tournamentId = tournamentId;
            return this;
        }

        public Game build() {
            Game game = new Game();
            game.setId(id);
            game.setId(id);
            game.setBracketIndex(bracketIndex);
            game.setStartTime(startTime);
            game.setEndTime(endTime);
            game.setType(type);
            game.setFirstPlayerId(firstPlayerId);
            game.setSecondPlayerId(secondPlayerId);
            game.setWinnerId(winnerId);
            game.setTournamentId(tournamentId);
            return game;
        }
    }
}
