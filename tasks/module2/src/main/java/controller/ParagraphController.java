package controller;

import model.ParagraphComposite;
import model.SentenceComposite;
import model.TextLeaf;
import service.CompositeService;

public class ParagraphController implements CompositeController<ParagraphComposite> {

    private CompositeService<ParagraphComposite> service;
    private CompositeController<SentenceComposite> childController;

    public ParagraphController(CompositeService<ParagraphComposite> service,
                                   CompositeController<SentenceComposite> sentenceController) {
        this.service = service;
        this.childController = sentenceController;
    }

    @Override
    public long add(ParagraphComposite item, long parentId) {
        return service.add(item, parentId);
    }

    @Override
    public void addAll(ParagraphComposite item, long parentId) {
        long id = add(item, parentId);
        for (TextLeaf leaf : item.getAll()) {
            childController.addAll((SentenceComposite) leaf, id);
        }
    }

}
