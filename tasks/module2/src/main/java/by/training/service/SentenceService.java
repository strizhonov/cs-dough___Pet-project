package by.training.service;

import by.training.model.SentenceComposite;
import by.training.model.TextComposite;
import by.training.repository.SentenceRepository;

import java.util.Comparator;
import java.util.List;

public class SentenceService {

    private SentenceRepository repository;
    private WordService wordService;

    public SentenceService(SentenceRepository repository,
                           WordService wordService) {
        this.repository = repository;
        this.wordService = wordService;
    }

    public long add(SentenceComposite item, long parentId) {
        return repository.create(item, parentId);
    }

    public List<SentenceComposite> compile() {
        return repository.compile();
    }

    public List<SentenceComposite> sort(Comparator<SentenceComposite> comparator) {
        List<SentenceComposite> sentences = compile();
        sentences.sort(comparator);
        return sentences;
    }

}
