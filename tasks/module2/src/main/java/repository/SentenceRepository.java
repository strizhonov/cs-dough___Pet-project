package repository;

import entity.Sentence;
import model.SentenceComposite;
import model.TextLeaf;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

;

public class SentenceRepository implements TextElementRepository<SentenceComposite> {

    private List<Sentence> sentences;
    private WordRepository childRepo;

    public SentenceRepository(WordRepository childRepo) {
        this.childRepo = childRepo;
        this.sentences = new LinkedList<>();
    }


    public List<Sentence> getData() {
        return new LinkedList<>(sentences);
    }

    @Override
    public long add(SentenceComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        Sentence sentence = new Sentence(id, parentId, text);
        sentences.add(sentence);
        return id;
    }

    public List<TextLeaf> getAll(ParentIdSpecification spec) {
        List<TextLeaf> sentenceComposites = new LinkedList<>();

        for (Sentence sentence : sentences) {
            if (!spec.isSatisfiedBy(sentence)) {
                continue;
            }
            long id = sentence.getId();
            ParentIdSpecification innerSpec = new ParentIdSpecification(id);
            List<TextLeaf> words = childRepo.getAll(innerSpec);
            SentenceComposite composite = new SentenceComposite(words);
            sentenceComposites.add(composite);
        }

        return sentenceComposites;
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
