package by.training.model;

public interface TextLeaf {
    String getText();

    long save(long parentId);

    void load(long id);
}
