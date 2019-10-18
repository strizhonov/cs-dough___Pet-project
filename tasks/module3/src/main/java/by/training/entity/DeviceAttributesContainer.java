package by.training.entity;

import java.util.Objects;

public class DeviceAttributesContainer {

    private boolean peripheral;
    private boolean cooler;
    private String group;
    private PortType port;

    public DeviceAttributesContainer(boolean peripheral, boolean cooler, String group, PortType port) {
        this.peripheral = peripheral;
        this.cooler = cooler;
        this.group = group;
        this.port = port;
    }

    public boolean isPeripheral() {
        return peripheral;
    }

    public void setPeripheral(boolean peripheral) {
        this.peripheral = peripheral;
    }

    public boolean isCooler() {
        return cooler;
    }

    public void setCooler(boolean cooler) {
        this.cooler = cooler;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public PortType getPort() {
        return port;
    }

    public void setPort(PortType port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceAttributesContainer)) return false;
        DeviceAttributesContainer that = (DeviceAttributesContainer) o;
        return isPeripheral() == that.isPeripheral() &&
                isCooler() == that.isCooler() &&
                Objects.equals(getGroup(), that.getGroup()) &&
                getPort() == that.getPort();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPeripheral(), isCooler(), getGroup(), getPort());
    }

    @Override
    public String toString() {
        return "DeviceAttributeContainer{" +
                "peripheral=" + peripheral +
                ", cooler=" + cooler +
                ", group='" + group + '\'' +
                ", port=" + port +
                '}';
    }

}
