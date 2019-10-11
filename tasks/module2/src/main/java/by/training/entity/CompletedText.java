package by.training.entity;

import java.util.Objects;

public class CompletedText extends BaseTextElement {

    private final TextType type;

    public CompletedText(long id, long parentId, String text) {
        super(id, parentId, text);
        this.type = TextType.COMPLETED;
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
        CompletedText that = (CompletedText) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    @Override
    public String toString() {
        return "CompletedText{" +
                "type=" + type +
                ", id=" + id +
                ", parentId=" + parentId +
                ", text='" + text + '\'' +
                '}';
    }
}
