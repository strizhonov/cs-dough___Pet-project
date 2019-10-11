package by.training.repository;

import by.training.entity.Sentence;
import by.training.model.SentenceComposite;
import by.training.model.TextLeaf;

import java.util.LinkedList;
import java.util.List;

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
    public long create(SentenceComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        Sentence sentence = new Sentence(id, parentId, text);
        sentences.add(sentence);
        return id;
    }

    public void delete(long id) {
        for (Sentence sentence : sentences) {
            if (sentence.getId() == id) {
                sentences.remove(sentence);
                break;
            }
        }
    }

    public List<TextLeaf> findAllBy(ParentIdSpecification spec) {
        List<TextLeaf> sentenceComposites = new LinkedList<>();

        for (Sentence sentence : sentences) {
            if (!spec.isSatisfiedBy(sentence)) {
                continue;
            }
            long id = sentence.getId();
            ParentIdSpecification innerSpec = new ParentIdSpecification(id);
            List<TextLeaf> words = childRepo.findAllBy(innerSpec);
            SentenceComposite composite = new SentenceComposite(words);
            sentenceComposites.add(composite);
        }

        return sentenceComposites;
    }

    public List<SentenceComposite> compile() {
        List<SentenceComposite> sentenceComposite = new LinkedList<>();

        for (Sentence sentence : sentences) {
            long id = sentence.getId();
            ParentIdSpecification spec = new ParentIdSpecification(id);
            List<TextLeaf> words = childRepo.findAllBy(spec);
            SentenceComposite composite = new SentenceComposite(words);
            sentenceComposite.add(composite);
        }

        return sentenceComposite;
    }

}
