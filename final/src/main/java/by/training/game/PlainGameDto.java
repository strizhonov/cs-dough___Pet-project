package by.training.game;

import by.training.player.PlainPlayerDto;
import by.training.player.PlayerDto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PlainGameDto implements Serializable {

    private static final long serialVersionUID = 4L;

    protected long id;
    protected int bracketIndex;
    protected Date startTime;
    protected Date endTime;
    protected long firstPlayerId;
    protected long secondPlayerId;
    protected long tournamentId;
    protected long gameServerId;


    public PlainGameDto() {
    }


    public PlainGameDto(long id, int bracketIndex, Date startTime, Date endTime, long firstPlayerId,
                        long secondPlayerId, long tournamentId, long gameServerId) {
        this.id = id;
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
        this.gameServerId = gameServerId;
    }


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

    public long getGameServerId() {
        return gameServerId;
    }

    public void setGameServerId(long gameServerId) {
        this.gameServerId = gameServerId;
    }

    public boolean hasEmptySlot() {
        return firstPlayerId == 0 || secondPlayerId == 0;
    }


    /**
     * Sets player id to the first found empty (equals 0) slot.
     *
     * @param player to set entity.
     * @return slot order number. If there is no empty slot returns -1.
     */
    public int occupyPlayerSlot(PlainPlayerDto player) {
        if (firstPlayerId == 0) {
            firstPlayerId = player.getId();
            return 0;
        }
        if (secondPlayerId == 0) {
            secondPlayerId = player.getId();
            return 1;
        }
        return -1;
    }


    /**
     * Clears player from corresponding slot.
     *
     * @param player to clear from slot.
     * @return cleared slot number. If there is no such player on both slots, returns -1.
     */
    public int releasePlayerSlot(PlayerDto player) {
        if (firstPlayerId == player.getId()) {
            firstPlayerId = 0;
            return 0;
        }
        if (secondPlayerId == player.getId()) {
            secondPlayerId = 0;
            return 0;
        }
        return -1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainGameDto that = (PlainGameDto) o;
        return id == that.id &&
                bracketIndex == that.bracketIndex &&
                firstPlayerId == that.firstPlayerId &&
                secondPlayerId == that.secondPlayerId &&
                tournamentId == that.tournamentId &&
                gameServerId == that.gameServerId &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, bracketIndex, startTime, endTime, firstPlayerId, secondPlayerId, tournamentId, gameServerId);
    }


    @Override
    public String toString() {
        return "PlainGameDto{" +
                "id=" + id +
                ", bracketIndex=" + bracketIndex +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", firstPlayerId=" + firstPlayerId +
                ", secondPlayerId=" + secondPlayerId +
                ", tournamentId=" + tournamentId +
                ", gameServerId=" + gameServerId +
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

        public Builder firstPlayerId(long firstPlayerId) {
            this.firstPlayerId = firstPlayerId;
            return this;
        }

        public Builder secondPlayerId(long secondPlayerId) {
            this.secondPlayerId = secondPlayerId;
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

        public PlainGameDto build() {
            PlainGameDto plainGameDto = new PlainGameDto();
            plainGameDto.setId(id);
            plainGameDto.setBracketIndex(bracketIndex);
            plainGameDto.setStartTime(startTime);
            plainGameDto.setEndTime(endTime);
            plainGameDto.setFirstPlayerId(firstPlayerId);
            plainGameDto.setSecondPlayerId(secondPlayerId);
            plainGameDto.setTournamentId(tournamentId);
            plainGameDto.setGameServerId(gameServerId);
            return plainGameDto;
        }
    }


}



