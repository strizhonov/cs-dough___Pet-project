package by.training.organizer;

import java.io.Serializable;
import java.util.Objects;

public class OrganizerValidationDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long logoSize;
    private String name;

    public OrganizerValidationDto() {
    }

    public OrganizerValidationDto(long logoSize, String name) {
        this.logoSize = logoSize;
        this.name = name;
    }

    public long getLogoSize() {
        return logoSize;
    }

    public void setLogoSize(long logoSize) {
        this.logoSize = logoSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizerValidationDto that = (OrganizerValidationDto) o;
        return logoSize == that.logoSize &&
                Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(logoSize, name);
    }


    @Override
    public String toString() {
        return "OrganizerValidationDto{" +
                "logoSize=" + logoSize +
                ", name='" + name + '\'' +
                '}';
    }


}
