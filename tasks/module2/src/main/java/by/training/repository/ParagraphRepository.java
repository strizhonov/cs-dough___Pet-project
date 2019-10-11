package by.training.repository;

import by.training.entity.Paragraph;
import by.training.model.ParagraphComposite;
import by.training.model.TextLeaf;

import java.util.LinkedList;
import java.util.List;

public class ParagraphRepository implements TextElementRepository<ParagraphComposite> {

    private List<Paragraph> paragraphs;
    private SentenceRepository childRepo;

    public ParagraphRepository(SentenceRepository childRepo) {
        this.childRepo = childRepo;
        paragraphs = new LinkedList<>();
    }

    @Override
    public long create(ParagraphComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        Paragraph paragraph = new Paragraph(id, parentId, text);
        paragraphs.add(paragraph);
        return id;
    }

    public void delete(long id) {
        for (Paragraph paragraph : paragraphs) {
            if (paragraph.getId() == id) {
                paragraphs.remove(paragraph);
                break;
            }
        }
    }

    public List<TextLeaf> findAllBy(ParentIdSpecification spec) {
        List<TextLeaf> paragraphComposites = new LinkedList<>();

        for (Paragraph paragraph : paragraphs) {
            if (!spec.isSatisfiedBy(paragraph)) {
                continue;
            }
            long id = paragraph.getId();
            ParentIdSpecification innerSpec = new ParentIdSpecification(id);
            List<TextLeaf> sentences = childRepo.findAllBy(innerSpec);
            ParagraphComposite composite = new ParagraphComposite(sentences);
            paragraphComposites.add(composite);
        }

        return paragraphComposites;
    }

    public List<ParagraphComposite> compile() {
        List<ParagraphComposite> paragraphsComposite = new LinkedList<>();

        for (Paragraph paragraph : paragraphs) {
            long id = paragraph.getId();
            ParentIdSpecification spec = new ParentIdSpecification(id);
            List<TextLeaf> sentences = childRepo.findAllBy(spec);
            ParagraphComposite composite = new ParagraphComposite(sentences);
            paragraphsComposite.add(composite);
        }

        return paragraphsComposite;
    }

}