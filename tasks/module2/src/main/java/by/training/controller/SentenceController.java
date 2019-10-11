package by.training.controller;

import by.training.model.SentenceComposite;
import by.training.model.TextComposite;
import by.training.model.TextLeaf;
import by.training.model.WordLeaf;
import by.training.service.SentenceService;

import java.util.Comparator;
import java.util.List;

public class SentenceController {

    private SentenceService service;
    private WordController childController;

    public SentenceController(SentenceService service,
                              WordController wordController) {
        this.service = service;
        this.childController = wordController;
    }

    public long add(SentenceComposite item, long parentId) {
        return service.add(item, parentId);
    }

    public void addAll(SentenceComposite item, long parentId) {
        long id = add(item, parentId);
        for (TextLeaf leaf : item.getAll()) {
            childController.add((WordLeaf) leaf, id);
        }
    }

    public List<SentenceComposite> sortChildren(boolean asc) {
        Comparator<TextComposite> comparator = new LeafNumberComparator(asc);
        return service.sortChildren(comparator);
    }

}
