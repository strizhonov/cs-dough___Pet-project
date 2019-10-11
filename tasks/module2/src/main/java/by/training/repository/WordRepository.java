package by.training.repository;

import by.training.entity.Word;
import by.training.model.TextLeaf;
import by.training.model.WordLeaf;

import java.util.LinkedList;
import java.util.List;

public class WordRepository implements TextElementRepository<WordLeaf> {

    private List<Word> words = new LinkedList<>();

    public List<Word> getData() {
        return new LinkedList<>(words);
    }

    @Override
    public long create(WordLeaf item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        Word word = leafToWord(item, id, parentId);
        words.add(word);
        return id;
    }

    public void delete(long id) {
        for (Word word : words) {
            if (word.getId() == id) {
                words.remove(word);
                break;
            }
        }
    }

    public List<TextLeaf> findAllBy(ParentIdSpecification spec) {
        List<TextLeaf> wordLeaves = new LinkedList<>();

        for (Word word : words) {
            if (!spec.isSatisfiedBy(word)) {
                continue;
            }
            WordLeaf leaf = wordToLeaf(word);
            wordLeaves.add(leaf);
        }

        return wordLeaves;
    }

    private Word leafToWord(WordLeaf item, long id, long parentId) {
        String text = item.getWordText();
        String before = item.getBefore();
        String after = item.getAfter();
        return new Word(id, parentId, before, text, after);
    }

    private WordLeaf wordToLeaf(Word item) {
        String text = item.getText();
        String before = item.getBefore();
        String after = item.getAfter();
        return new WordLeaf(before, text, after);
    }

}
