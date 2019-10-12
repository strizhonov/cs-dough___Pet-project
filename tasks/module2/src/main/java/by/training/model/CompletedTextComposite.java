package by.training.model;

import by.training.entity.BaseTextElement;
import by.training.entity.CompletedText;
import by.training.entity.Paragraph;
import by.training.repository.ParentIdSpecification;
import by.training.repository.TextElementSpecification;
import by.training.service.CompletedTextService;
import by.training.service.ParagraphService;
import by.training.service.SentenceService;
import by.training.service.WordService;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CompletedTextComposite implements TextComposite {

    private static final String SEPARATOR = "\n";
    private List<TextLeaf> paragraphs = new LinkedList<>();
    private CompletedTextService service;
    private ParagraphService childService;
    private SentenceService sentenceService;
    private WordService wordService;

    @Override
    public void add(TextLeaf paragraph) {
        paragraphs.add(paragraph);
    }

    @Override
    public List<TextLeaf> getAll() {
        return paragraphs;
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

        paragraphs.clear();
        paragraphs.addAll(composites);
    }

    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();

        for (TextLeaf paragraph : paragraphs) {
            sb.append(paragraph.getText());
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    @Override
    public long save(long parentId) {
        String text = getText();
        CompletedText element = new CompletedText(-1, parentId, text);
        long id = service.add(element);
        saveChildren(id);
        return id;
    }

    @Override
    public void load(TextElementSpecification<BaseTextElement> spec) {
        List<Paragraph> paragraphs = childService.find(spec);
        for (Paragraph paragraph : paragraphs) {
            long id = paragraph.getId();
            ParentIdSpecification spec1 = new ParentIdSpecification(id);
            ParagraphComposite composite = new ParagraphComposite();
            composite.setService(childService);
            composite.setChildService(sentenceService);
            composite.setWordService(wordService);
            composite.load(spec1);
            this.paragraphs.add(composite);
        }
    }

    public void setService(CompletedTextService service) {
        this.service = service;
    }

    public void setChildService(ParagraphService childService) {
        this.childService = childService;
    }

    public void setSentenceService(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    public void setWordService(WordService wordService) {
        this.wordService = wordService;
    }

    private Optional<List<TextComposite>> getChildrenAsComposite() {
        List<TextComposite> children = new LinkedList<>();
        for (TextLeaf paragraph : paragraphs) {
            if (paragraph instanceof TextComposite) {
                children.add((TextComposite) paragraph);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(children);
    }

    private void saveChildren(long parentId) {
        for (TextLeaf leaf : paragraphs) {
            ParagraphComposite composite = (ParagraphComposite) leaf;
            composite.setService(childService);
            composite.setChildService(sentenceService);
            composite.setWordService(wordService);
            composite.save(parentId);
        }
    }

}
