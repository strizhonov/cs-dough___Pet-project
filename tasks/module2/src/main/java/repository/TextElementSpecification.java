package repository;

import entity.BaseTextElement;

public interface TextElementSpecification<T extends BaseTextElement> {
    boolean isSatisfiedBy(T entity);
}
