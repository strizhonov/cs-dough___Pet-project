package by.training.entity;

public class Organizer extends Entity {

    private String name;
    private byte[] logo = new byte[0];
    private long userId;

    public Organizer() {
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

    public static final class Builder {
        private long id;
        private String name;
        private byte[] logo = new byte[0];
        private long userId;

        private Builder() {
        }

        public static Builder anOrganizer() {
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

        public Organizer build() {
            Organizer organizer = new Organizer();
            organizer.setId(id);
            organizer.setName(name);
            organizer.setLogo(logo);
            organizer.setUserId(userId);
            return organizer;
        }
    }
}
