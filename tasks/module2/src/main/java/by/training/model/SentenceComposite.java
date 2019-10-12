package by.training.model;

import by.training.entity.Sentence;
import by.training.entity.Word;
import by.training.repository.ParentIdSpecification;
import by.training.service.SentenceService;
import by.training.service.WordService;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SentenceComposite implements TextComposite {

    private static final Logger LOGGER = Logger.getLogger(SentenceComposite.class);
    private static final String SEPARATOR = " ";
    private List<TextLeaf> words = new LinkedList<>();
    private SentenceService service;
    private WordService childService;

    @Override
    public void add(TextLeaf word) {
        words.add(word);
    }

    @Override
    public List<TextLeaf> getAll() {
        return words;
    }

    @Override
    public void sortChildrenByLeavesCount(boolean asc) {
        Optional<List<TextComposite>> optional = getChildrenAsComposite();
        if (!optional.isPresent()) {
            return;
        }

        List<TextComposite> composites = optional.get();
        Comparator<TextComposite> comparator = new LeavesNumberComparator(asc);
        composites.sort(comparator);

        words.clear();
        words.addAll(composites);
    }

    @Override
    public long save(long parentId) {
        if (service == null) {
            LOGGER.error("No service found.");
            return -1;
        }
        String text = getText();
        Sentence element = new Sentence(-1, parentId, text);
        long id = service.add(element);
        saveChildren(id);
        return id;
    }

    @Override
    public void load(long parentId) {
        if (childService == null) {
            LOGGER.error("No service found.");
            return;
        }
        ParentIdSpecification spec = new ParentIdSpecification(parentId);
        List<Word> words = childService.find(spec);
        for (Word word : words) {
            long id = word.getId();
            WordLeaf leaf = new WordLeaf();
            leaf.setService(childService);
            leaf.load(id);
            this.words.add(leaf);
        }
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();

        for (TextLeaf word : words) {
            sb.append(word.getText());
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    public void setService(SentenceService service) {
        this.service = service;
    }

    public void setChildService(WordService childService) {
        this.childService = childService;
    }

    private Optional<List<TextComposite>> getChildrenAsComposite() {
        List<TextComposite> children = new LinkedList<>();
        for (TextLeaf word : words) {
            if (word instanceof TextComposite) {
                children.add((TextComposite) word);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(children);
    }

    private void saveChildren(long parentId) {
        for (TextLeaf leaf : words) {
            WordLeaf wordLeaf = (WordLeaf) leaf;
            wordLeaf.setService(childService);
            wordLeaf.save(parentId);
        }
    }

}
