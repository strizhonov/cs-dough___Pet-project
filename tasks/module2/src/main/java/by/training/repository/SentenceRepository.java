package by.training.repository;

import by.training.entity.Sentence;
import by.training.model.SentenceComposite;
import by.training.model.TextLeaf;
import by.training.model.WordLeaf;

import java.util.LinkedList;
import java.util.List;

public class SentenceRepository implements TextElementRepository<SentenceComposite> {

    private List<Sentence> sentences;
    private TextElementRepository<WordLeaf> childRepo;

    public SentenceRepository(TextElementRepository<WordLeaf> childRepo) {
        this.childRepo = childRepo;
        this.sentences = new LinkedList<>();
    }

    @Override
    public long create(SentenceComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        Sentence sentence = new Sentence(id, parentId, text);
        sentences.add(sentence);
        return id;
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
    public List<SentenceComposite> compile() {
        List<SentenceComposite> sentenceComposite = new LinkedList<>();

        for (Sentence sentence : sentences) {
            long id = sentence.getId();
            ParentIdSpecification spec = new ParentIdSpecification(id);
            List<TextLeaf> words = childRepo.find(spec);
            SentenceComposite composite = new SentenceComposite(words);
            sentenceComposite.add(composite);
        }

        return sentenceComposite;
    }

    @Override
    public List<TextLeaf> find(ParentIdSpecification spec) {
        List<TextLeaf> sentenceComposites = new LinkedList<>();

        for (Sentence sentence : sentences) {
            if (!spec.isSatisfiedBy(sentence)) {
                continue;
            }
            long id = sentence.getId();
            ParentIdSpecification innerSpec = new ParentIdSpecification(id);
            List<TextLeaf> words = childRepo.find(innerSpec);
            SentenceComposite composite = new SentenceComposite(words);
            sentenceComposites.add(composite);
        }

        return sentenceComposites;
    }

}
