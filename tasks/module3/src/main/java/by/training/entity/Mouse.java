package by.training.entity;

import java.util.Objects;

public class Mouse extends Device {

    private int buttons;

    public Mouse(long id, String name, int price, String origin,
                 DeviceAttributesContainer attributesContainer, boolean critical, int buttons) {
        super(id, name, price, origin, attributesContainer, critical, DeviceType.MOUSE);
        this.buttons = buttons;
    }

    public int getButtons() {
        return buttons;
    }

    public void setButtons(int buttons) {
        this.buttons = buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mouse)) return false;
        Mouse mouse = (Mouse) o;
        return getButtons() == mouse.getButtons();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getButtons());
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "buttons=" + buttons +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", attributesContainer=" + attributesContainer +
                ", critical=" + critical +
                ", type=" + type +
                '}';
    }
}