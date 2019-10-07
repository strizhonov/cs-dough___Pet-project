package controller;

import model.CompletedTextComposite;
import model.ParagraphComposite;
import model.TextLeaf;
import parserchain.ParagraphParser;
import parserchain.SentenceParser;
import parserchain.WordParser;
import service.CompletedTextService;

import java.util.List;

public class CompletedTextController implements CompositeController<CompletedTextComposite> {

    private CompletedTextService service;
    private CompositeController<ParagraphComposite> childController;
    private ParagraphParser paragraphParser;
    private SentenceParser sentenceParser;
    private WordParser wordParser;

    public CompletedTextController(CompletedTextService service,
                                   CompositeController<ParagraphComposite> childController,
                                   ParagraphParser paragraphParser,
                                   SentenceParser sentenceParser,
                                   WordParser wordParser) {
        this.service = service;
        this.childController = childController;
        this.paragraphParser = paragraphParser;
        this.sentenceParser = sentenceParser;
        this.wordParser = wordParser;
    }

    @Override
    public long add(CompletedTextComposite item, long parentId) {
        return service.add(item, parentId);
    }

    @Override
    public void addAll(CompletedTextComposite item, long parentId) {
        long id = add(item, 0);
        for (TextLeaf leaf : item.getAll()) {
            childController.addAll((ParagraphComposite) leaf, id);
        }
    }

    public CompletedTextComposite parse(String text) {
        paragraphParser.linkWith(sentenceParser);
        sentenceParser.linkWith(wordParser);

        return (CompletedTextComposite) paragraphParser.parse(text);
    }

    public List<CompletedTextComposite> compile() {
        return service.compile();
    }

}
