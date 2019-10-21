package by.training.entity;

import java.util.Objects;

public class MotherBoard extends Device {

    private int ramSlots;

    public MotherBoard(String id, String name, int price, String origin, DeviceProperties attributesContainer,
                       boolean critical, int ramSlots) {
        super(id, name, price, origin, attributesContainer, critical, DeviceType.MOTHERBOARD);
        this.ramSlots = ramSlots;
    }

    public int getRamSlots() {
        return ramSlots;
    }

    public void setRamSlots(int ramSlots) {
        this.ramSlots = ramSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotherBoard)) return false;
        MotherBoard that = (MotherBoard) o;
        return getRamSlots() == that.getRamSlots();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRamSlots());
    }

    @Override
    public String toString() {
        return "MotherBoard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", attributesContainer=" + attributesContainer +
                ", critical=" + critical +
                ", type=" + type +
                "ramSlots=" + ramSlots +
                '}';
    }

    public static class Builder extends Device.Builder {

        private int ramSlots;

        public Builder setRamSlots(int ramSlots) {
            this.ramSlots = ramSlots;
            return this;
        }

        public MotherBoard build() {
            return new MotherBoard(id, name, price, origin, attributesContainer, critical, ramSlots);
        }
    }

}
