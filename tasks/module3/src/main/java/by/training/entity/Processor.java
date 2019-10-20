package by.training.entity;

import java.util.Objects;

public class Processor extends Device {

    private int consumption;

    public Processor(String id, String name, int price, String origin,
                     DeviceAttributesContainer attributesContainer, boolean critical, int consumption) {
        super(id, name, price, origin, attributesContainer, critical, DeviceType.PROCESSOR);
        this.consumption = consumption;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Processor)) return false;
        Processor processor = (Processor) o;
        return getConsumption() == processor.getConsumption();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getConsumption());
    }

    @Override
    public String toString() {
        return "Processor{" +
                "consumption=" + consumption +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", attributesContainer=" + attributesContainer +
                ", critical=" + critical +
                ", type=" + type +
                '}';
    }

    public static class Builder extends Device.Builder {

        private int consumption;

        public Builder setConsumption(int consumption) {
            this.consumption = consumption;
            return this;
        }

        public Processor build() {
            return new Processor(id, name, price, origin, attributesContainer, critical, consumption);
        }
    }

}
