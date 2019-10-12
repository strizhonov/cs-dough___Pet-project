package by.training.model;

import java.util.List;
import java.util.Optional;

public interface TextComposite extends TextLeaf {

    void add(TextLeaf text);

    List<TextLeaf> getAll();

    void sortChildrenByLeavesCount(boolean asc);

    Optional<List<TextComposite>> getChildrenAsComposite();

}
