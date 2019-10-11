package by.training.service;

import by.training.model.ParagraphComposite;
import by.training.model.TextComposite;
import by.training.repository.ParagraphRepository;

import java.util.Comparator;
import java.util.List;

public class ParagraphService {

    private ParagraphRepository repository;
    private SentenceService sentenceService;

    public ParagraphService(ParagraphRepository repository,
                            SentenceService sentenceService) {
        this.repository = repository;
        this.sentenceService = sentenceService;
    }

    public long add(ParagraphComposite item, long parentId) {
        return repository.create(item, parentId);
    }


    public List<ParagraphComposite> compile() {
        return repository.compile();
    }

    public List<ParagraphComposite> sortChildren(Comparator<TextComposite> comparator) {
        List<ParagraphComposite> paragraphs = compile();
        paragraphs.sort(comparator);
        return paragraphs;
    }
}
