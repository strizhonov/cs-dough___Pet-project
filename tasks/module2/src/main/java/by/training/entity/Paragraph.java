package by.training.entity;

import java.util.Objects;

public class Paragraph extends BaseTextElement {

    private final TextType type;

    public Paragraph(long id, long parentId, String text) {
        super(id, parentId, text);
        this.type = TextType.PARAGRAPH;
    }

    public TextType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Paragraph paragraph = (Paragraph) o;
        return getType() == paragraph.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "type=" + type +
                ", id=" + id +
                ", parentId=" + parentId +
                ", text='" + text + '\'' +
                '}';
    }
}
