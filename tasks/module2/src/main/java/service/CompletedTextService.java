package service;

import model.CompletedTextComposite;
import model.ParagraphComposite;
import repository.CompletedTextRepository;

import java.util.List;

public class CompletedTextService implements CompositeService<CompletedTextComposite> {

    private CompletedTextRepository repository;
    private CompositeService<ParagraphComposite> paragraphService;

    public CompletedTextService(CompletedTextRepository repository,
                                CompositeService<ParagraphComposite> paragraphService) {
        this.repository = repository;
        this.paragraphService = paragraphService;
    }

    @Override
    public long add(CompletedTextComposite item, long parentId) {
        return repository.add(item, parentId);
    }

    public List<CompletedTextComposite> compile() {
        return repository.compile();
    }

}
