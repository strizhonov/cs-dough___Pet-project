package by.training.repository;

public interface TextElementSpecification<T> {
    boolean isSatisfiedBy(T entity);
}
