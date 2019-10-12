package by.training.repository;

import by.training.entity.BaseTextElement;
import by.training.entity.Word;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class WordRepository implements TextElementRepository<Word> {

    private List<Word> words = new LinkedList<>();

    public List<Word> getData() {
        return new LinkedList<>(words);
    }

    @Override
    public long create(Word item) {
        long id = IdCreator.getInstance().getUniqueId();
        item.setId(id);
        words.add(item);
        return id;
    }

    @Override
    public Optional<Word> get(long id) {
        for (Word word : words) {
            if (word.getId() == id) {
                return Optional.of(word);
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Word item) {
        long id = item.getId();
        delete(id);
        create(item);
    }

    @Override
    public void delete(long id) {
        for (Word word : words) {
            if (word.getId() == id) {
                words.remove(word);
                break;
            }
        }
    }

    @Override
    public List<Word> find(TextElementSpecification<BaseTextElement> spec) {
        List<Word> found = new LinkedList<>();

        for (Word word : words) {
            if (!spec.isSatisfiedBy(word)) {
                continue;
            }
            found.add(word);
        }

        return found;
    }

}
