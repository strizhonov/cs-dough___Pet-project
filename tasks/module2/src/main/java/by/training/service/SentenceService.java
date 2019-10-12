package by.training.service;

import by.training.entity.BaseTextElement;
import by.training.entity.Sentence;
import by.training.repository.TextElementRepository;
import by.training.repository.TextElementSpecification;

import java.util.List;
import java.util.Optional;

public class SentenceService implements TextService<Sentence> {

    private TextElementRepository<Sentence> repository;

    public SentenceService(TextElementRepository<Sentence> repository) {
        this.repository = repository;

    }

    @Override
    public long add(Sentence element) {
        return repository.create(element);
    }

    @Override
    public Optional<Sentence> get(long id) {
        return repository.get(id);
    }

    @Override
    public List<Sentence> find(TextElementSpecification<BaseTextElement> spec) {
        return repository.find(spec);
    }


}
