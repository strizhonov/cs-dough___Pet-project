package service;

import model.ParagraphComposite;
import model.SentenceComposite;
import repository.TextElementRepository;

public class ParagraphService implements CompositeService<ParagraphComposite> {

    private TextElementRepository<ParagraphComposite> repository;
    private CompositeService<SentenceComposite> sentenceService;

    public ParagraphService(TextElementRepository<ParagraphComposite> repository,
                            CompositeService<SentenceComposite> sentenceService) {
        this.repository = repository;
        this.sentenceService = sentenceService;
    }

    @Override
    public long add(ParagraphComposite item, long parentId) {
        return repository.add(item, parentId);
    }

}
