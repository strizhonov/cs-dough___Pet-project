package by.training.service;

import by.training.entity.BaseTextElement;
import by.training.entity.CompletedText;
import by.training.repository.TextElementRepository;
import by.training.repository.TextElementSpecification;

import java.util.List;
import java.util.Optional;

public class CompletedTextService implements TextService<CompletedText> {

    private TextElementRepository<CompletedText> repository;

    public CompletedTextService(TextElementRepository<CompletedText> repository) {
        this.repository = repository;
    }

    @Override
    public long add(CompletedText element) {
        return repository.create(element);
    }

    @Override
    public Optional<CompletedText> get(long id) {
        return repository.get(id);
    }

    @Override
    public List<CompletedText> find(TextElementSpecification<BaseTextElement> spec) {
        return repository.find(spec);
    }

}
