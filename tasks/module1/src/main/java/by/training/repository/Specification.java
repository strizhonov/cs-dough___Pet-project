package by.training.repository;

public interface Specification<T> {

    boolean isSatisfiedBy(T entity);

    default Specification<T> and(Specification<T> spec) {
        return entity -> this.isSatisfiedBy(entity)
                && spec.isSatisfiedBy(entity);
    }

    default Specification<T> or(Specification<T> spec) {
        return entity -> this.isSatisfiedBy(entity)
                || spec.isSatisfiedBy(entity);
    }

    default Specification<T> not(Specification<T> spec) {
        return entity -> !spec.isSatisfiedBy(entity);
    }

}
