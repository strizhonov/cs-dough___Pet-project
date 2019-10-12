package by.training.repository;

import by.training.entity.BaseTextElement;
import by.training.entity.CompletedText;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CompletedTextRepository implements TextElementRepository<CompletedText> {

    private List<CompletedText> texts = new LinkedList<>();

    public List<CompletedText> getData() {
        return new LinkedList<>(texts);
    }

    @Override
    public long create(CompletedText item) {
        long id = IdCreator.getInstance().getUniqueId();
        item.setId(id);
        texts.add(item);
        return id;
    }

    @Override
    public Optional<CompletedText> get(long id) {
        for (CompletedText text : texts) {
            if (text.getId() == id) {
                return Optional.of(text);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(CompletedText item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (CompletedText text : texts) {
            if (text.getId() == id) {
                texts.remove(text);
                break;
            }
        }
    }

    @Override
    public List<CompletedText> find(TextElementSpecification<BaseTextElement> spec) {
        List<CompletedText> found = new LinkedList<>();

        for (CompletedText text : texts) {
            if (!spec.isSatisfiedBy(text)) {
                continue;
            }
            found.add(text);
        }

        return found;
    }

}
