package by.training.service;

import by.training.model.*;
import by.training.repository.SentenceRepository;
import java.util.Comparator;
import java.util.List;

public class SentenceService {

    private SentenceRepository repository;
    private WordService childService;

    public SentenceService(SentenceRepository repository,
                           WordService childService) {
        this.repository = repository;
        this.childService = childService;
    }

    public void addAll(SentenceComposite item, long parentId) {
        long id = add(item, parentId);
        for (TextLeaf leaf : item.getAll()) {
            childService.add((WordLeaf) leaf, id);
        }
    }

    public List<SentenceComposite> sort(boolean asc) {
        List<SentenceComposite> sentences = repository.compile();
        Comparator<SentenceComposite> comparator = new WordNumberComparator(asc);
        sentences.sort(comparator);
        return sentences;
    }

    private long add(SentenceComposite item, long parentId) {
        return repository.create(item, parentId);
    }

}
