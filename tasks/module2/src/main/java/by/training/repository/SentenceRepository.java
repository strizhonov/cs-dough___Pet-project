package by.training.repository;

import by.training.entity.BaseTextElement;
import by.training.entity.Sentence;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SentenceRepository implements TextElementRepository<Sentence> {

    private List<Sentence> sentences = new LinkedList<>();

    public List<Sentence> getData() {
        return new LinkedList<>(sentences);
    }

    @Override
    public long create(Sentence item) {
        long id = IdCreator.getInstance().getUniqueId();
        item.setId(id);
        sentences.add(item);
        return id;
    }

    @Override
    public Optional<Sentence> get(long id) {
        for (Sentence sentence : sentences) {
            if (sentence.getId() == id) {
                return Optional.of(sentence);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Sentence item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (Sentence sentence : sentences) {
            if (sentence.getId() == id) {
                sentences.remove(sentence);
                break;
            }
        }
    }

    @Override
    public List<Sentence> find(TextElementSpecification<BaseTextElement> spec) {
        List<Sentence> found = new LinkedList<>();

        for (Sentence sentence : sentences) {
            if (!spec.isSatisfiedBy(sentence)) {
                continue;
            }
            found.add(sentence);
        }

        return found;
    }

}
