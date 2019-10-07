package model;

public class WordLeaf implements TextLeaf {

    private final String before;
    private final String text;
    private final String after;

    public WordLeaf(String before, String text, String after) {
        this.before = before;
        this.text = text;
        this.after = after;
    }

    @Override
    public String getText() {
        return before + text + after;
    }

    public String getWordText() {
        return text;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }


}
