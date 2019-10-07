package model;

import java.util.List;

public interface TextComposite extends TextLeaf {
    void add(TextLeaf text);
    List<TextLeaf> getAll();
}
