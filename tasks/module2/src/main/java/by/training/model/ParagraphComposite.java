package by.training.model;

import by.training.entity.BaseTextElement;
import by.training.entity.Paragraph;
import by.training.entity.Sentence;
import by.training.repository.ParentIdSpecification;
import by.training.repository.TextElementSpecification;
import by.training.service.ParagraphService;
import by.training.service.SentenceService;
import by.training.service.WordService;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ParagraphComposite implements TextComposite {

    private static final String PREFIX = "\t";
    private List<TextLeaf> sentences = new LinkedList<>();
    private ParagraphService service;
    private SentenceService childService;
    private WordService wordService;

    @Override
    public void add(TextLeaf sentence) {
        sentences.add(sentence);
    }

    @Override
    public List<TextLeaf> getAll() {
        return sentences;
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

        sentences.clear();
        sentences.addAll(composites);
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);

        for (TextLeaf sentence : sentences) {
            sb.append(sentence.getText());
        }

        return sb.toString();
    }

    @Override
    public long save(long parentId) {
        String text = getText();
        Paragraph element = new Paragraph(-1, parentId, text);
        long id = service.add(element);
        saveChildren(id);
        return id;
    }

    @Override
    public void load(TextElementSpecification<BaseTextElement> spec) {
        List<Sentence> sentences = childService.find(spec);
        for (Sentence sentence : sentences) {
            long id = sentence.getId();
            ParentIdSpecification spec1 = new ParentIdSpecification(id);
            SentenceComposite composite = new SentenceComposite();
            composite.setService(childService);
            composite.setChildService(wordService);
            composite.load(spec1);
            this.sentences.add(composite);
        }
    }

    public void setService(ParagraphService service) {
        this.service = service;
    }

    public void setChildService(SentenceService childService) {
        this.childService = childService;
    }

    public void setWordService(WordService wordService) {
        this.wordService = wordService;
    }

    private Optional<List<TextComposite>> getChildrenAsComposite() {
        List<TextComposite> children = new LinkedList<>();
        for (TextLeaf sentence : sentences) {
            if (sentence instanceof TextComposite) {
                children.add((TextComposite) sentence);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(children);
    }

    private void saveChildren(long parentId) {
        for (TextLeaf leaf : sentences) {
            SentenceComposite composite = (SentenceComposite) leaf;
            composite.setService(childService);
            composite.setChildService(wordService);
            composite.save(parentId);
        }
    }

}
