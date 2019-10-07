package service;

import model.SentenceComposite;
import model.WordLeaf;
import repository.TextElementRepository;

public class SentenceService implements CompositeService<SentenceComposite> {

    private TextElementRepository<SentenceComposite> repository;
    private CompositeService<WordLeaf> wordService;

    public SentenceService(TextElementRepository<SentenceComposite> repository,
                           CompositeService<WordLeaf> wordService) {
        this.repository = repository;
        this.wordService = wordService;
    }

    @Override
    public long add(SentenceComposite item, long parentId) {
        return repository.add(item, parentId);
    }

}
