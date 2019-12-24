package by.training.player;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class PlayerValidationDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long photoSize;
    private String photoType;
    private byte[] photo;
    private String nickname;
    private String name;
    private String surname;


    public PlayerValidationDto() {
    }


    public PlayerValidationDto(long photoSize, String photoType, byte[] photo, String nickname, String name,
                               String surname) {
        this.photoSize = photoSize;
        this.photoType = photoType;
        if (photo == null) {
            this.photo = null;
        } else {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
        this.photo = photo;
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(long photoSize) {
        this.photoSize = photoSize;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public byte[] getPhoto() {
        if (photo == null) {
            return null;
        } else {
            return Arrays.copyOf(photo, photo.length);
        }
    }

    public void setPhoto(byte[] photo) {
        if (photo == null) {
            this.photo = null;
        } else {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerValidationDto that = (PlayerValidationDto) o;
        return photoSize == that.photoSize &&
                Objects.equals(photoType, that.photoType) &&
                Arrays.equals(photo, that.photo) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(photoSize, photoType, nickname, name, surname);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }


    @Override
    public String toString() {
        return "PlayerValidationDto{" +
                "photoSize=" + photoSize +
                ", photoType='" + photoType + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

}


