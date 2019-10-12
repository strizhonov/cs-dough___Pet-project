package by.training.repository;

import by.training.entity.BaseTextElement;

public class IdSpecification implements TextElementSpecification<BaseTextElement> {

    private long id;

    public IdSpecification(long id) {
        this.id = id;
    }

    @Override
    public boolean isSatisfiedBy(BaseTextElement item) {
        return item.getId() == id;
    }

}
