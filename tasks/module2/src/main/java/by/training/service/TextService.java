package by.training.service;

import by.training.entity.BaseTextElement;
import by.training.repository.TextElementSpecification;

import java.util.List;
import java.util.Optional;

public interface TextService<T extends BaseTextElement> {
    long add(T element);

    Optional<T> get(long id);

    List<T> find(TextElementSpecification<BaseTextElement> spec);
}