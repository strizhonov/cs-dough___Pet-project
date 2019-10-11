package by.training.controller;

import by.training.model.ParagraphComposite;
import by.training.model.SentenceComposite;
import by.training.model.TextLeaf;
import by.training.service.ParagraphService;

import java.util.List;

public class ParagraphController {

    private ParagraphService service;
    private SentenceController childController;

    public ParagraphController(ParagraphService service,
                               SentenceController sentenceController) {
        this.service = service;
        this.childController = sentenceController;
    }

    public long add(ParagraphComposite item, long parentId) {
        return service.add(item, parentId);
    }

    public void addAll(ParagraphComposite item, long parentId) {
        long id = add(item, parentId);
        for (TextLeaf leaf : item.getAll()) {
            childController.addAll((SentenceComposite) leaf, id);
        }
    }

    public List<ParagraphComposite> compile() {
        return service.compile();
    }

    public List<SentenceComposite> sortSentencesByWords(boolean asc) {
        return childController.sort(asc);
    }

}
