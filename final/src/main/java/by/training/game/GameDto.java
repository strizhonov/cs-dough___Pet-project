package by.training.game;

import by.training.common.BaseDto;

import java.util.Date;

public class GameDto extends BaseDto {

    private long id;
    /**
     * Bracket index logic:
     *
     *                                   i=0
     *                                    final
     *
     *                    i=1                                i=2
     *                semi-final                          semi-final
     *
     *       i=3              i=4               i=5               i=6
     * quarter final     quarter final     quarter final     quarter final
     */
    private int bracketIndex;
    private Date startTime;
    private Date endTime;
    private long firstPlayerId;
    private long secondPlayerId;
    private long winnerId;
    private long tournamentId;
    private long gameServerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getGameServerId() {
        return gameServerId;
    }

    public void setGameServerId(long gameServerId) {
        this.gameServerId = gameServerId;
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
        private long gameServerId;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder bracketIndex(int bracketIndex) {
            this.bracketIndex = bracketIndex;
            return this;
        }

        public Builder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder firstPlayerId(long firstPlayerId) {
            this.firstPlayerId = firstPlayerId;
            return this;
        }

        public Builder secondPlayerId(long secondPlayerId) {
            this.secondPlayerId = secondPlayerId;
            return this;
        }

        public Builder winnerId(long winnerId) {
            this.winnerId = winnerId;
            return this;
        }

        public Builder tournamentId(long tournamentId) {
            this.tournamentId = tournamentId;
            return this;
        }

        public Builder gameServerId(long gameServerId) {
            this.gameServerId = gameServerId;
            return this;
        }

        public GameDto build() {
            GameDto gameDto = new GameDto();
            gameDto.setId(id);
            gameDto.setBracketIndex(bracketIndex);
            gameDto.setStartTime(startTime);
            gameDto.setEndTime(endTime);
            gameDto.setFirstPlayerId(firstPlayerId);
            gameDto.setSecondPlayerId(secondPlayerId);
            gameDto.setWinnerId(winnerId);
            gameDto.setTournamentId(tournamentId);
            gameDto.setGameServerId(gameServerId);
            return gameDto;
        }
    }
}
