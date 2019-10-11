package by.training.service;

import by.training.model.CompletedTextComposite;
import by.training.model.ParagraphComposite;
import by.training.model.SentenceComposite;
import by.training.model.TextLeaf;
import by.training.repository.CompletedTextRepository;

import java.util.List;

public class CompletedTextService {

    private CompletedTextRepository repository;
    private ParagraphService childService;

    public CompletedTextService(CompletedTextRepository repository,
                                ParagraphService childService) {
        this.repository = repository;
        this.childService = childService;
    }

    private long add(CompletedTextComposite item, long parentId) {
        return repository.create(item, parentId);
    }

    public void addAll(CompletedTextComposite item, long parentId) {
        long id = add(item, 0);
        for (TextLeaf leaf : item.getAll()) {
            childService.addAll((ParagraphComposite) leaf, id);
        }
    }

    public List<CompletedTextComposite> compile() {
        return repository.compile();
    }

    public List<SentenceComposite> sortSentencesByWords(boolean asc) {
        return childService.sortSentencesByWords(asc);
    }
}
