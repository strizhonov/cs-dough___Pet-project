package model;

import java.util.LinkedList;
import java.util.List;

public class ParagraphComposite implements TextComposite {

    private static final String PREFIX = "\t";
    private List<TextLeaf> sentences;

    public ParagraphComposite() {
        sentences = new LinkedList<>();
    }

    public ParagraphComposite(List<TextLeaf> sentences) {
        this.sentences = sentences;
    }

    @Override
    public void add(TextLeaf sentence) {
        sentences.add(sentence);
    }

    @Override
    public List<TextLeaf> getAll() {
        return sentences;
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);

        for (TextLeaf sentence : sentences) {
            sb.append(sentence.getText());
        }

        return sb.toString();
    }

}
