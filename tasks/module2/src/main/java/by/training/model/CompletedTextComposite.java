package by.training.model;

import java.util.LinkedList;
import java.util.List;

public class CompletedTextComposite implements TextComposite {

    private static final String SEPARATOR = "\n";
    private List<TextLeaf> paragraphs;

    public CompletedTextComposite() {
        paragraphs = new LinkedList<>();
    }

    public CompletedTextComposite(List<TextLeaf> paragraphs) {
        this.paragraphs = paragraphs;
    }

    @Override
    public void add(TextLeaf paragraph) {
        paragraphs.add(paragraph);
    }

    @Override
    public List<TextLeaf> getAll() {
        return paragraphs;
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();

        for (TextLeaf paragraph : paragraphs) {
            sb.append(paragraph.getText());
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }
}
