package by.training.entity;

import java.util.Date;
import java.util.Objects;

public class Mouse extends Device {

    private int buttons;

    public Mouse(String id, String name, int price, String origin, DeviceProperties attributesContainer,
                 boolean critical, int buttons, Date manufacturingDate) {
        super(id, name, price, origin, attributesContainer, critical, DeviceType.MOUSE, manufacturingDate);
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

    public static class Builder extends Device.Builder {

        private int buttons;

        public Builder setButtons(int buttons) {
            this.buttons = buttons;
            return this;
        }

        public Mouse build() {
            return new Mouse(id, name, price, origin, attributesContainer, critical, buttons, manufacturingDate);
        }
    }


}
