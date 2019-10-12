package by.training.service;

import by.training.entity.BaseTextElement;
import by.training.entity.Paragraph;
import by.training.repository.TextElementRepository;
import by.training.repository.TextElementSpecification;

import java.util.List;
import java.util.Optional;

public class ParagraphService implements TextService<Paragraph> {

    private TextElementRepository<Paragraph> repository;

    public ParagraphService(TextElementRepository<Paragraph> repository) {
        this.repository = repository;
    }

    @Override
    public long add(Paragraph element) {
        return repository.create(element);
    }

    @Override
    public Optional<Paragraph> get(long id) {
        return repository.get(id);
    }

    @Override
    public List<Paragraph> find(TextElementSpecification<BaseTextElement> spec) {
        return repository.find(spec);
    }
}
