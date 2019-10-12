package by.training.model;

import by.training.entity.BaseTextElement;
import by.training.entity.Word;
import by.training.repository.TextElementSpecification;
import by.training.service.WordService;

import java.util.List;

public class WordLeaf implements TextLeaf {

    private String before;
    private String text;
    private String after;
    private WordService service;

    public WordLeaf() {
    }

    public WordLeaf(String before, String text, String after) {
        this.before = before;
        this.text = text;
        this.after = after;
    }

    public void setService(WordService service) {
        this.service = service;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public String getText() {
        return before + text + after;
    }

    @Override
    public long save(long parentId) {
        Word word = new Word(-1, parentId, before, text, after);
        return service.add(word);
    }

    @Override
    public void load(TextElementSpecification<BaseTextElement> specification) {
        List<Word> words = service.find(specification);
        Word word = words.get(0);

        this.before = word.getBefore();
        this.text = word.getText();
        this.after = word.getAfter();

    }
}

