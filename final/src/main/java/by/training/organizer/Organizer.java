package by.training.organizer;

import by.training.core.Entity;

import java.util.Arrays;
import java.util.Objects;

public class Organizer extends Entity {

    private static final long serialVersionUID = 4L;

    private byte[] logo = new byte[0];
    private String name;
    private long userId;

    public Organizer() {
    }

    public Organizer(byte[] logo, String name, long userId) {
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        this.name = name;
        this.userId = userId;
    }

    private Organizer(Builder builder) {
        setId(builder.id);
        logo = builder.logo;
        name = builder.name;
        userId = builder.userId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizer organizer = (Organizer) o;
        return userId == organizer.userId &&
                Arrays.equals(logo, organizer.logo) &&
                Objects.equals(name, organizer.name);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(name, userId);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }


    @Override
    public String toString() {
        return "Organizer{" +
                "logo=" + Arrays.toString(logo) +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }


    public static final class Builder {
        private long id;
        private byte[] logo = new byte[0];
        private String name;
        private long userId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder logo(byte[] val) {
            if (val == null) {
                this.logo = null;
            } else {
                this.logo = Arrays.copyOf(val, val.length);
            }
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder userId(long val) {
            userId = val;
            return this;
        }

        public Organizer build() {
            return new Organizer(this);
        }
    }
}
