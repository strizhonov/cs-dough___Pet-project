package controller;

import model.SentenceComposite;
import model.TextLeaf;
import model.WordLeaf;
import service.CompositeService;

public class SentenceController implements CompositeController<SentenceComposite> {

    private CompositeService<SentenceComposite> service;
    private CompositeController<WordLeaf> childController;

    public SentenceController(CompositeService<SentenceComposite> service,
                               CompositeController<WordLeaf> wordController) {
        this.service = service;
        this.childController = wordController;
    }

    @Override
    public long add(SentenceComposite item, long parentId) {
        return service.add(item, parentId);
    }

    @Override
    public void addAll(SentenceComposite item, long parentId) {
        long id = add(item, parentId);
        for (TextLeaf leaf : item.getAll()) {
            childController.add((WordLeaf) leaf, id);
        }
    }

}
