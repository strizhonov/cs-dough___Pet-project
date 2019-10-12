package by.training.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public void sortChildrenByLeavesCount(boolean asc) {
        Optional<List<TextComposite>> optional = getChildrenAsComposite();
        if (!optional.isPresent()) {
            return;
        }

        List<TextComposite> composites = optional.get();
        Comparator<TextComposite> comparator = new LeavesNumberComparator(asc);
        composites.sort(comparator);

        sentences.clear();
        sentences.addAll(composites);
    }

    @Override
    public Optional<List<TextComposite>> getChildrenAsComposite() {
        List<TextComposite> children = new LinkedList<>();
        for (TextLeaf sentence : sentences) {
            if (sentence instanceof TextComposite) {
                children.add((TextComposite) sentence);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(children);
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
