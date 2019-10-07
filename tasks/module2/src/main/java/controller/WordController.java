package controller;

import model.WordLeaf;
import service.CompositeService;

public class WordController implements CompositeController<WordLeaf> {

    private CompositeService<WordLeaf> service;

    public WordController(CompositeService<WordLeaf> service) {
        this.service = service;
    }

    @Override
    public long add(WordLeaf item, long parentId) {
        return service.add(item, parentId);
    }

    @Override
    public void addAll(WordLeaf item, long parentId) {
        service.add(item, parentId);
    }

}
