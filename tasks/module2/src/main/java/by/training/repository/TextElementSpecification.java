package by.training.repository;

import by.training.entity.BaseTextElement;

public interface TextElementSpecification<T extends BaseTextElement> {
    boolean isSatisfiedBy(T entity);
}
