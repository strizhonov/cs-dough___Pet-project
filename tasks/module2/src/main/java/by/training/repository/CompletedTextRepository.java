package by.training.repository;

import by.training.entity.CompletedText;
import by.training.model.CompletedTextComposite;
import by.training.model.TextLeaf;

import java.util.LinkedList;
import java.util.List;

public class CompletedTextRepository implements TextElementRepository<CompletedTextComposite> {

    private List<CompletedText> texts;
    private ParagraphRepository childRepo;

    public CompletedTextRepository(ParagraphRepository childRepo) {
        this.childRepo = childRepo;
        this.texts = new LinkedList<>();
    }

    public List<CompletedText> getData() {
        return new LinkedList<>(texts);
    }

    @Override
    public long create(CompletedTextComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        CompletedText completedText = new CompletedText(id, parentId, text);
        texts.add(completedText);
        return id;
    }

    public void delete(long id) {
        for (CompletedText text : texts) {
            if (text.getId() == id) {
                texts.remove(text);
                break;
            }
        }
    }

    public List<CompletedTextComposite> compile() {
        List<CompletedTextComposite> completedTextComposites = new LinkedList<>();

        for (CompletedText text : texts) {
            long id = text.getId();
            ParentIdSpecification spec = new ParentIdSpecification(id);
            List<TextLeaf> paragraphs = childRepo.findAllBy(spec);
            CompletedTextComposite composite = new CompletedTextComposite(paragraphs);
            completedTextComposites.add(composite);
        }

        return completedTextComposites;
    }

}
