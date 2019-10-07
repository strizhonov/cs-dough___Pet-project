package repository;

import entity.CompletedText;
import model.CompletedTextComposite;
import model.TextLeaf;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    public long add(CompletedTextComposite item, long parentId) {
        long id = IdCreator.getInstance().getUniqueId();
        String text = item.getText();
        CompletedText completedText = new CompletedText(id, parentId, text);
        texts.add(completedText);
        return id;
    }

    public List<CompletedTextComposite> compile() {
        List<CompletedTextComposite> completedTextComposites = new LinkedList<>();

        for (CompletedText text : texts) {
            long id = text.getId();
            ParentIdSpecification spec = new ParentIdSpecification(id);
            List<TextLeaf> paragraphs = childRepo.getAll(spec);
            CompletedTextComposite composite = new CompletedTextComposite(paragraphs);
            completedTextComposites.add(composite);
        }

        return completedTextComposites;
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
