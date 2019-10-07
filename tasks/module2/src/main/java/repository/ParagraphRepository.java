package repository;

import entity.Paragraph;
import model.ParagraphComposite;
import model.TextLeaf;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParagraphRepository implements TextElementRepository<ParagraphComposite> {

    private List<Paragraph> paragraphs;
    private SentenceRepository childRepo;

    public ParagraphRepository(SentenceRepository childRepo) {
        this.childRepo = childRepo;
        paragraphs = new LinkedList<>();
    }

    @Override
    public long add(ParagraphComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        Paragraph paragraph = new Paragraph(id, parentId, text);
        paragraphs.add(paragraph);
        return id;
    }

    public List<TextLeaf> getAll(ParentIdSpecification spec) {
        List<TextLeaf> paragraphComposites = new LinkedList<>();

        for (Paragraph paragraph : paragraphs) {
            if (!spec.isSatisfiedBy(paragraph)) {
                continue;
            }
            long id = paragraph.getId();
            ParentIdSpecification innerSpec = new ParentIdSpecification(id);
            List<TextLeaf> sentences = childRepo.getAll(innerSpec);
            ParagraphComposite composite = new ParagraphComposite(sentences);
            paragraphComposites.add(composite);
        }

        return paragraphComposites;
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
