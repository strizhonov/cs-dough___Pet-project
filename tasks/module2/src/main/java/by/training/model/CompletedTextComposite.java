package by.training.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public void sortChildrenByLeavesCount(boolean asc) {
        Optional<List<TextComposite>> optional = getChildrenAsComposite();
        if (!optional.isPresent()) {
            return;
        }

        List<TextComposite> composites = optional.get();
        Comparator<TextComposite> comparator = new LeavesNumberComparator(asc);
        composites.sort(comparator);

        paragraphs.clear();
        paragraphs.addAll(composites);
    }

    @Override
    public Optional<List<TextComposite>> getChildrenAsComposite() {
        List<TextComposite> children = new LinkedList<>();
        for (TextLeaf paragraph : paragraphs) {
            if (paragraph instanceof TextComposite) {
                children.add((TextComposite) paragraph);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(children);
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
