package entity;

import java.util.Objects;

public class PlainWarehouse {

    private long id;
    private int capacity;
    private int cargoWeight;
    private int terminalsCount;

    public PlainWarehouse(long id, int capacity, int cargoWeight, int terminalsCount) {
        this.id = id;
        this.capacity = capacity;
        this.cargoWeight = cargoWeight;
        this.terminalsCount = terminalsCount;
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

    public int getTerminalsCount() {
        return terminalsCount;
    }

    public void setTerminalsCount(int terminalsCount) {
        this.terminalsCount = terminalsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainWarehouse that = (PlainWarehouse) o;
        return id == that.id &&
                capacity == that.capacity &&
                cargoWeight == that.cargoWeight &&
                terminalsCount == that.terminalsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, cargoWeight, terminalsCount);
    }

    @Override
    public String toString() {
        return "PlainWarehouse{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", cargoWeight=" + cargoWeight +
                ", terminalsCount=" + terminalsCount +
                '}';
    }

    public static class Builder {

        private long id;
        private int capacity;
        private int cargoWeight;
        private int terminalsCount;

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

        public Builder setTerminalsCount(int terminalsCount) {
            this.terminalsCount = terminalsCount;
            return this;
        }

        public PlainWarehouse build() {
            return new PlainWarehouse(id, capacity, cargoWeight, terminalsCount);
        }
    }

}
