package by.training.game;

import by.training.core.Entity;

import java.util.Date;
import java.util.Objects;

public class Game extends Entity {

    private static final long serialVersionUID = 4L;
    /**
     * Bracket index logic:
     * <p>
     * i=0
     * final
     * <p>
     * i=1                                i=2
     * semi-final                          semi-final
     * <p>
     * i=3              i=4               i=5               i=6
     * quarter final     quarter final     quarter final     quarter final
     */
    private int bracketIndex;
    private Date startTime;
    private Date endTime;
    private long firstPlayerId;
    private long secondPlayerId;
    private long tournamentId;


    public Game() {
    }


    public Game(int bracketIndex, Date startTime, Date endTime, long firstPlayerId,
                long secondPlayerId, long tournamentId) {
        this.bracketIndex = bracketIndex;
        if (startTime == null) {
            this.startTime = null;
        } else {
            this.startTime = new Date(startTime.getTime());
        }
        if (endTime == null) {
            this.endTime = null;
        } else {
            this.endTime = new Date(endTime.getTime());
        }
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.tournamentId = tournamentId;
    }


    private Game(Builder builder) {
        setId(builder.id);
        setBracketIndex(builder.bracketIndex);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setFirstPlayerId(builder.firstPlayerId);
        setSecondPlayerId(builder.secondPlayerId);
        setTournamentId(builder.tournamentId);
    }


    public int getBracketIndex() {
        return bracketIndex;
    }

    public void setBracketIndex(int bracketIndex) {
        this.bracketIndex = bracketIndex;
    }

    public Date getStartTime() {
        if (startTime == null) {
            return null;
        } else {
            return new Date(startTime.getTime());
        }
    }

    public void setStartTime(Date startTime) {
        if (startTime == null) {
            this.startTime = null;
        } else {
            this.startTime = new Date(startTime.getTime());
        }
    }

    public Date getEndTime() {
        if (endTime == null) {
            return null;
        } else {
            return new Date(endTime.getTime());
        }
    }

    public void setEndTime(Date endTime) {
        if (endTime == null) {
            this.endTime = null;
        } else {
            this.endTime = new Date(endTime.getTime());
        }
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

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return bracketIndex == game.bracketIndex &&
                firstPlayerId == game.firstPlayerId &&
                secondPlayerId == game.secondPlayerId &&
                tournamentId == game.tournamentId &&
                Objects.equals(startTime, game.startTime) &&
                Objects.equals(endTime, game.endTime);
    }


    @Override
    public int hashCode() {
        return Objects.hash(bracketIndex, startTime, endTime, firstPlayerId, secondPlayerId, tournamentId);
    }


    @Override
    public String toString() {
        return "Game{" +
                "bracketIndex=" + bracketIndex +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", firstPlayerId=" + firstPlayerId +
                ", secondPlayerId=" + secondPlayerId +
                ", tournamentId=" + tournamentId +
                ", id=" + id +
                '}';
    }


    public static final class Builder {
        private long id;
        private int bracketIndex;
        private Date startTime;
        private Date endTime;
        private long firstPlayerId;
        private long secondPlayerId;
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

        public Builder startTime(Date startTime) {
            if (startTime == null) {
                this.startTime = null;
            } else {
                this.startTime = new Date(startTime.getTime());
            }
            return this;
        }

        public Builder endTime(Date endTime) {
            if (endTime == null) {
                this.endTime = null;
            } else {
                this.endTime = new Date(endTime.getTime());
            }
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

        public Builder tournamentId(long val) {
            tournamentId = val;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }

}
