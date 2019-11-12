package entity;

import java.util.Objects;

public class PlainTruck {

    private long id;
    private int capacity;
    private int cargoWeight;
    private boolean perishable;

    public PlainTruck(long id, int capacity, int cargoWeight, boolean perishable) {
        this.id = id;
        this.capacity = capacity;
        this.cargoWeight = cargoWeight;
        this.perishable = perishable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainTruck that = (PlainTruck) o;
        return id == that.id &&
                capacity == that.capacity &&
                cargoWeight == that.cargoWeight &&
                perishable == that.perishable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, cargoWeight, perishable);
    }

    @Override
    public String toString() {
        return "PlainTruck{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", cargoWeight=" + cargoWeight +
                ", perishable=" + perishable +
                '}';
    }

    public static class Builder {

        private long id;
        private int capacity;
        private int cargoWeight;
        private boolean perishable;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setCargoWeight(int cargoWeight) {
            this.cargoWeight = cargoWeight;
            return this;
        }

        public Builder setPerishable(boolean perishable) {
            this.perishable = perishable;
            return this;
        }

        public PlainTruck build() {
            return new PlainTruck(id, capacity, cargoWeight, perishable);
        }

    }

}
