package by.training.model;

import by.training.entity.BaseTextElement;
import by.training.repository.TextElementSpecification;

public interface TextLeaf {
    String getText();

    long save(long parentId);

    void load(TextElementSpecification<BaseTextElement> specification);
}
