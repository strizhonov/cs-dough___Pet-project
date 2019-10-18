package by.training.entity;

public abstract class Device {

    protected long id;
    protected String name;
    protected int price;
    protected String origin;
    protected DeviceAttributesContainer attributesContainer;
    protected boolean critical;
    protected DeviceType type;

    public Device(long id, String name, int price, String origin,
                  DeviceAttributesContainer attributesContainer, boolean critical, DeviceType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.attributesContainer = attributesContainer;
        this.critical = critical;
        this.type = type;
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

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

}