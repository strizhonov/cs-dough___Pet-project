package by.training.model;

import by.training.entity.Word;
import by.training.service.WordService;
import org.apache.log4j.Logger;

import java.util.Optional;

public class WordLeaf implements TextLeaf {

    private static final Logger LOGGER = Logger.getLogger(WordLeaf.class);
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
        if (service == null) {
            LOGGER.error("No service found.");
            return -1;
        }
        Word word = new Word(-1, parentId, before, text, after);
        return service.add(word);
    }

    @Override
    public void load(long id) {
        if (service == null) {
            LOGGER.error("No service found.");
            return;
        }
        Optional<Word> optWord = service.get(id);
        if (!optWord.isPresent()) {
            return;
        }

        Word word = optWord.get();
        this.before = word.getBefore();
        this.text = word.getText();
        this.after = word.getAfter();

    }
}

