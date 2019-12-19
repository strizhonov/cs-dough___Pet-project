package by.training.organizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizerDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long id;
    private String name;
    private byte[] logo = new byte[0];
    private long userId;
    private List<Long> tournamentsIdList = new ArrayList<>();


    public OrganizerDto() {
    }


    public OrganizerDto(long id, String name, byte[] logo, long userId, List<Long> tournamentsIdList) {
        this.id = id;
        this.name = name;
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        this.userId = userId;
        this.tournamentsIdList = tournamentsIdList;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        if (logo == null) {
            return null;
        } else {
            return Arrays.copyOf(logo, logo.length);
        }
    }

    public void setLogo(byte[] logo) {
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getTournamentsIdList() {
        return tournamentsIdList;
    }

    public void setTournamentsIdList(List<Long> tournamentsIdList) {
        this.tournamentsIdList = tournamentsIdList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizerDto that = (OrganizerDto) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(name, that.name) &&
                Arrays.equals(logo, that.logo) &&
                Objects.equals(tournamentsIdList, that.tournamentsIdList);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, userId, tournamentsIdList);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }


    @Override
    public String toString() {
        return "OrganizerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo=" + Arrays.toString(logo) +
                ", userId=" + userId +
                ", tournamentsIdList=" + tournamentsIdList +
                '}';
    }


    public static final class Builder {
        private long id;
        private String name;
        private byte[] logo = new byte[0];
        private long userId;
        private List<Long> tournamentsIdList = new ArrayList<>();

        private Builder() {
        }

        public static Builder anOrganizerDto() {
            return new Builder();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder logo(byte[] logo) {
            if (logo == null) {
                this.logo = null;
            } else {
                this.logo = Arrays.copyOf(logo, logo.length);
            }
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder tournamentsIdList(List<Long> tournamentsIdList) {
            this.tournamentsIdList = tournamentsIdList;
            return this;
        }

        public OrganizerDto build() {
            OrganizerDto organizerDto = new OrganizerDto();
            organizerDto.setId(id);
            organizerDto.setName(name);
            organizerDto.setLogo(logo);
            organizerDto.setUserId(userId);
            organizerDto.setTournamentsIdList(tournamentsIdList);
            return organizerDto;
        }
    }

}
