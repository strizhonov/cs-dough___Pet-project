package by.training.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public void sortChildrenByLeavesCount(boolean asc) {
        Optional<List<TextComposite>> optional = getChildrenAsComposite();
        if (!optional.isPresent()) {
            return;
        }

        List<TextComposite> composites = optional.get();
        Comparator<TextComposite> comparator = new LeavesNumberComparator(asc);
        composites.sort(comparator);

        words.clear();
        words.addAll(composites);
    }

    @Override
    public Optional<List<TextComposite>> getChildrenAsComposite() {
        List<TextComposite> children = new LinkedList<>();
        for (TextLeaf word : words) {
            if (word instanceof TextComposite) {
                children.add((TextComposite) word);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(children);
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
