package by.training.service;

import by.training.entity.BaseTextElement;
import by.training.entity.Word;
import by.training.repository.TextElementRepository;
import by.training.repository.TextElementSpecification;

import java.util.List;
import java.util.Optional;

public class WordService implements TextService<Word> {

    private TextElementRepository<Word> repository;

    public WordService(TextElementRepository<Word> repository) {
        this.repository = repository;
    }

    @Override
    public long add(Word element) {
        return repository.create(element);
    }

    @Override
    public Optional<Word> get(long id) {
        return repository.get(id);
    }

    @Override
    public List<Word> find(TextElementSpecification<BaseTextElement> spec) {
        return repository.find(spec);
    }

}
