package by.training.model;

import java.util.LinkedList;
import java.util.List;

public class SentenceComposite implements TextComposite {

    private static final String SEPARATOR = " ";
    private List<TextLeaf> words;

    public SentenceComposite() {
        words = new LinkedList<>();
    }

    public SentenceComposite(List<TextLeaf> words) {
        this.words = words;
    }

    @Override
    public void add(TextLeaf word) {
        words.add(word);
    }

    @Override
    public List<TextLeaf> getAll() {
        return words;
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();

        for (TextLeaf word : words) {
            sb.append(word.getText());
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }
}
