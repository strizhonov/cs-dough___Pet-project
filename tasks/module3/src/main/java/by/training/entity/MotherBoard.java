package by.training.entity;

import java.util.Objects;

public class MotherBoard extends Device {

    private int ramSlots;

    public MotherBoard(long id, String name, int price, String origin, DeviceAttributesContainer attributesContainer,
                       boolean critical, int ramSlots) {
        super(id, name, price, origin, attributesContainer, critical, DeviceType.MOTHER_BOARD);
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

}
