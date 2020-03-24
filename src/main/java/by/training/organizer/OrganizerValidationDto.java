package by.training.organizer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class OrganizerValidationDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long logoSize;
    private String logoType;
    private byte[] logo;
    private String name;


    public OrganizerValidationDto() {
    }


    public OrganizerValidationDto(long logoSize, String logoType, byte[] logo, String name) {
        this.logoSize = logoSize;
        this.logoType = logoType;
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        this.logo = logo;
        this.name = name;
    }


    public long getLogoSize() {
        return logoSize;
    }

    public void setLogoSize(long logoSize) {
        this.logoSize = logoSize;
    }

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(String logoType) {
        this.logoType = logoType;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizerValidationDto that = (OrganizerValidationDto) o;
        return logoSize == that.logoSize &&
                Objects.equals(logoType, that.logoType) &&
                Arrays.equals(logo, that.logo) &&
                Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(logoSize, logoType, name);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }


    @Override
    public String toString() {
        return "OrganizerValidationDto{" +
                "logoSize=" + logoSize +
                ", logoType='" + logoType + '\'' +
                ", logo=" + Arrays.toString(logo) +
                ", name='" + name + '\'' +
                '}';
    }

}
