package service;

import model.WordLeaf;
import repository.TextElementRepository;

public class WordService implements CompositeService<WordLeaf> {

    private TextElementRepository<WordLeaf> repository;

    public WordService(TextElementRepository<WordLeaf> repository) {
        this.repository = repository;
    }

    @Override
    public long add(WordLeaf item, long parentId) {
        return repository.add(item, parentId);
    }

}
