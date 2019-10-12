package by.training.repository;

import by.training.entity.CompletedText;
import by.training.model.CompletedTextComposite;
import by.training.model.ParagraphComposite;
import by.training.model.TextLeaf;

import java.util.LinkedList;
import java.util.List;

public class CompletedTextRepository implements TextElementRepository<CompletedTextComposite> {

    private List<CompletedText> texts;
    private TextElementRepository<ParagraphComposite> childRepo;

    public CompletedTextRepository(TextElementRepository<ParagraphComposite> childRepo) {
        this.childRepo = childRepo;
        this.texts = new LinkedList<>();
    }

    @Override
    public long create(CompletedTextComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        CompletedText completedText = new CompletedText(id, parentId, text);
        texts.add(completedText);
        return id;
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
    public List<CompletedTextComposite> compile() {
        List<CompletedTextComposite> completedTextComposites = new LinkedList<>();

        for (CompletedText text : texts) {
            long id = text.getId();
            ParentIdSpecification spec = new ParentIdSpecification(id);
            List<TextLeaf> paragraphs = childRepo.find(spec);
            CompletedTextComposite composite = new CompletedTextComposite(paragraphs);
            completedTextComposites.add(composite);
        }

        return completedTextComposites;
    }

    @Override
    public List<TextLeaf> find(ParentIdSpecification spec) {
        List<TextLeaf> textComposites = new LinkedList<>();

        for (CompletedText text : texts) {
            if (!spec.isSatisfiedBy(text)) {
                continue;
            }
            long id = text.getId();
            ParentIdSpecification innerSpec = new ParentIdSpecification(id);
            List<TextLeaf> paragraphs = childRepo.find(innerSpec);
            CompletedTextComposite composite = new CompletedTextComposite(paragraphs);
            textComposites.add(composite);
        }

        return textComposites;
    }

}
