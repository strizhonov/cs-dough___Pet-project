package by.training.service;


import by.training.model.CompletedTextComposite;
import by.training.repository.CompletedTextRepository;

import java.util.List;

public class CompletedTextService {

    private CompletedTextRepository repository;
    private ParagraphService paragraphService;

    public CompletedTextService(CompletedTextRepository repository,
                                ParagraphService paragraphService) {
        this.repository = repository;
        this.paragraphService = paragraphService;
    }

    public long add(CompletedTextComposite item, long parentId) {
        return repository.create(item, parentId);
    }

    public List<CompletedTextComposite> compile() {
        return repository.compile();
    }

}
