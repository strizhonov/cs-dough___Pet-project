package by.training.service;

import by.training.model.WordLeaf;
import by.training.repository.TextElementRepository;

public class WordService {

    private TextElementRepository<WordLeaf> repository;

    public WordService(TextElementRepository<WordLeaf> repository) {
        this.repository = repository;
    }

    public long add(WordLeaf item, long parentId) {
        return repository.create(item, parentId);
    }

}
