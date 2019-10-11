package by.training.entity;

import java.util.Objects;

public class Word extends BaseTextElement {

    private final TextType type;
    private final String before;
    private final String after;

    public Word(long id, long parentId, String text, String before, String after) {
        super(id, parentId, text);
        this.type = TextType.WORD;
        this.before = before;
        this.after = after;
    }

    public TextType getType() {
        return type;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
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
         Word word = (Word) o;
        return getType() == word.getType() &&
                Objects.equals(getBefore(), word.getBefore()) &&
                Objects.equals(getAfter(), word.getAfter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), getBefore(), getAfter());
    }

    @Override
    public String toString() {
        return "Word{" +
                "type=" + type +
                ", before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", id=" + id +
                ", parentId=" + parentId +
                ", text='" + text + '\'' +
                '}';
    }
}
