package by.training.entity;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public class Game extends Entity {

    private int bracketIndex;
    private Date startTime;
    private Date endTime;
    private long firstPlayerId;
    private long secondPlayerId;
    private long winnerId;
    private long tournamentId;

    private Game(Builder builder) {
        setId(builder.id);
        setBracketIndex(builder.bracketIndex);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setFirstPlayerId(builder.firstPlayerId);
        setSecondPlayerId(builder.secondPlayerId);
        setWinnerId(builder.winnerId);
        setTournamentId(builder.tournamentId);
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

    public static final class Builder {
        private long id;
        private int bracketIndex;
        private Date startTime;
        private Date endTime;
        private long firstPlayerId;
        private long secondPlayerId;
        private long winnerId;
        private long tournamentId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder bracketIndex(int val) {
            bracketIndex = val;
            return this;
        }

        public Builder startTime(Date val) {
            startTime = val;
            return this;
        }

        public Builder endTime(Date val) {
            endTime = val;
            return this;
        }

        public Builder firstPlayerId(long val) {
            firstPlayerId = val;
            return this;
        }

        public Builder secondPlayerId(long val) {
            secondPlayerId = val;
            return this;
        }

        public Builder winnerId(long val) {
            winnerId = val;
            return this;
        }

        public Builder tournamentId(long val) {
            tournamentId = val;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
