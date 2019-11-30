package by.training.dto;

import java.util.List;

public class OrganizerDto {

    private long id;
    private String name;
    private byte[] logo = new byte[0];
    private long userId;
    private List<Long> tournamentsIdList;

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
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
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


    public static final class Builder {
        private long id;
        private String name;
        private byte[] logo = new byte[0];
        private long userId;
        private List<Long> tournamentsIdList;

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
            this.logo = logo;
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
