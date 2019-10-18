package by.training.entity;

import java.util.Objects;

public class Device {

    private long id;
    private String name;
    private int price;
    private String origin;
    private DeviceAttributesContainer attributesContainer;
    private boolean critical;

    public Device(long id, String name, int price, String origin,
                  DeviceAttributesContainer attributesContainer, boolean critical) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.attributesContainer = attributesContainer;
        this.critical = critical;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public DeviceAttributesContainer getAttributesContainer() {
        return attributesContainer;
    }

    public void setAttributesContainer(DeviceAttributesContainer attributesContainer) {
        this.attributesContainer = attributesContainer;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return getId() == device.getId() &&
                getPrice() == device.getPrice() &&
                isCritical() == device.isCritical() &&
                Objects.equals(getName(), device.getName()) &&
                Objects.equals(getOrigin(), device.getOrigin()) &&
                Objects.equals(getAttributesContainer(), device.getAttributesContainer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getOrigin(), getAttributesContainer(), isCritical());
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", attributeContainer=" + attributesContainer +
                ", critical=" + critical +
                '}';
    }
}
