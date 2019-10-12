package by.training.service;

import by.training.model.ParagraphComposite;
import by.training.model.SentenceComposite;
import by.training.model.TextLeaf;
import by.training.repository.TextElementRepository;

import java.util.List;

public class ParagraphService {

    private TextElementRepository<ParagraphComposite> repository;
    private SentenceService childService;

    public ParagraphService(TextElementRepository<ParagraphComposite> repository,
                            SentenceService childService) {
        this.repository = repository;
        this.childService = childService;
    }

    public void addAll(ParagraphComposite item, long parentId) {
        long id = add(item, parentId);
        for (TextLeaf leaf : item.getAll()) {
            childService.addAll((SentenceComposite) leaf, id);
        }
    }

    public List<ParagraphComposite> compile() {
        return repository.compile();
    }

    public List<SentenceComposite> sortSentencesByWords(boolean asc) {
        return childService.sort(asc);
    }

    private long add(ParagraphComposite item, long parentId) {
        return repository.create(item, parentId);
    }
}
