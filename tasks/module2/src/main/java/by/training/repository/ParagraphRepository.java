package by.training.repository;

import by.training.entity.BaseTextElement;
import by.training.entity.Paragraph;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ParagraphRepository implements TextElementRepository<Paragraph> {

    private List<Paragraph> paragraphs = new LinkedList<>();

    public List<Paragraph> getData() {
        return new LinkedList<>(paragraphs);
    }

    @Override
    public long create(Paragraph item) {
        long id = IdCreator.getInstance().getUniqueId();
        item.setId(id);
        paragraphs.add(item);
        return id;
    }

    @Override
    public Optional<Paragraph> get(long id) {
        for (Paragraph paragraph : paragraphs) {
            if (paragraph.getId() == id) {
                return Optional.of(paragraph);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Paragraph item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (Paragraph paragraph : paragraphs) {
            if (paragraph.getId() == id) {
                paragraphs.remove(paragraph);
                break;
            }
        }
    }

    @Override
    public List<Paragraph> find(TextElementSpecification<BaseTextElement> spec) {
        List<Paragraph> found = new LinkedList<>();

        for (Paragraph paragraph : paragraphs) {
            if (!spec.isSatisfiedBy(paragraph)) {
                continue;
            }
            found.add(paragraph);
        }

        return found;
    }

}
