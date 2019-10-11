package by.training.controller;

import by.training.model.WordLeaf;
import by.training.service.WordService;

public class WordController {

    private WordService service;

    public WordController(WordService service) {
        this.service = service;
    }

    public long add(WordLeaf item, long parentId) {
        return service.add(item, parentId);
    }

}
