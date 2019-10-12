package by.training.repository;

import by.training.entity.BaseTextElement;

public class ParentIdSpecification implements TextElementSpecification<BaseTextElement> {

    private long parentId;

    public ParentIdSpecification(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean isSatisfiedBy(BaseTextElement item) {
        return item.getParentId() == parentId;
    }

}
