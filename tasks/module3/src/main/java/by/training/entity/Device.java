package by.training.entity;

import java.util.Date;

public abstract class Device {

    protected String id;
    protected String name;
    protected int price;
    protected String origin;
    protected DeviceProperties attributesContainer;
    protected boolean critical;
    protected DeviceType type;
    protected Date manufacturingDate;

    public Device(String id, String name, int price, String origin,
                  DeviceProperties attributesContainer, boolean critical, DeviceType type, Date manufacturingDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.origin = origin;
        this.attributesContainer = attributesContainer;
        this.critical = critical;
        this.type = type;
        this.manufacturingDate = manufacturingDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public DeviceProperties getAttributesContainer() {
        return attributesContainer;
    }

    public void setAttributesContainer(DeviceProperties attributesContainer) {
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

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public static abstract class Builder {
        protected String id;
        protected String name;
        protected int price;
        protected String origin;
        protected DeviceProperties attributesContainer;
        protected boolean critical;
        protected Date manufacturingDate;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder setAttributesContainer(DeviceProperties attributesContainer) {
            this.attributesContainer = attributesContainer;
            return this;
        }

        public Builder setCritical(boolean critical) {
            this.critical = critical;
            return this;
        }

        public Builder setManufacturingDate(Date manufacturingDate) {
            this.manufacturingDate = manufacturingDate;
            return this;
        }

        public abstract Device build();
    }


}
