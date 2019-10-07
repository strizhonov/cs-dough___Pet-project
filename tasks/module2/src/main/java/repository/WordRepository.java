package repository;

import entity.Word;
import model.TextLeaf;
import model.WordLeaf;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WordRepository implements TextElementRepository<WordLeaf> {

    private List<Word> words = new LinkedList<>();

    public List<Word> getData() {
        return new LinkedList<>(words);
    }

    @Override
    public long add(WordLeaf item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        Word word = leafToWord(item, id, parentId);
        words.add(word);
        return id;
    }

    public List<TextLeaf> getAll(ParentIdSpecification spec) {
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

    private static class IdCreator {

        private static final int INIT_VALUE = 0;
        private static IdCreator INSTANCE = new IdCreator();

        private AtomicInteger seq;

        private IdCreator() {
            seq = new AtomicInteger(INIT_VALUE);
        }

        private int getUniqueId() {
            return seq.incrementAndGet();
        }

        private static IdCreator getInstance() {
            return INSTANCE;
        }
    }

}
