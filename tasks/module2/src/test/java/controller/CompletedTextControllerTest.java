package controller;

import model.CompletedTextComposite;
import model.ParagraphComposite;
import model.SentenceComposite;
import model.WordLeaf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import parserchain.ParagraphParser;
import parserchain.SentenceParser;
import parserchain.WordParser;
import repository.CompletedTextRepository;
import repository.ParagraphRepository;
import repository.SentenceRepository;
import repository.WordRepository;
import service.*;

import java.util.List;

@RunWith(JUnit4.class)
public class CompletedTextControllerTest {

    private CompletedTextRepository completedTextRepository;
    private SentenceRepository sentenceRepository;
    private CompletedTextController controller;
    private String text;

    @Before
    public void init() {

        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();

        WordRepository wordRepository = new WordRepository();
        sentenceRepository = new SentenceRepository(wordRepository);
        ParagraphRepository paragraphRepository = new ParagraphRepository(sentenceRepository);
        completedTextRepository = new CompletedTextRepository(paragraphRepository);

        CompositeService<WordLeaf> wordService = new WordService(wordRepository);
        CompositeService<SentenceComposite> sentenceService = new SentenceService(sentenceRepository, wordService);
        CompositeService<ParagraphComposite> paragraphService = new ParagraphService(paragraphRepository, sentenceService);
        CompletedTextService textService = new CompletedTextService(completedTextRepository, paragraphService);

        CompositeController<WordLeaf> wordController = new WordController(wordService);
        CompositeController<SentenceComposite> sentenceController = new SentenceController(sentenceService, wordController);
        CompositeController<ParagraphComposite> paragraphController = new ParagraphController(paragraphService, sentenceController);

        controller = new CompletedTextController(textService, paragraphController, paragraphParser, sentenceParser, wordParser);

        text = "\tIt has survived not only five centuries, but also the leap into electronic " +
                "typesetting, remaining essentially unchanged. It was popularised in the with the " +
                "release of Letraset sheets containing Lorem Ipsum passages, and more recently with " +
                "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. \n";
    }

    @Test
    public void addAll() {
        CompletedTextComposite textComposite = controller.parse(text);
        controller.addAll(textComposite, 0);
        Assert.assertEquals(1, completedTextRepository.getData().size());
        Assert.assertEquals(2, sentenceRepository.getData().size());
    }

    @Test
    public void add() {
        CompletedTextComposite textComposite = controller.parse(text);
        controller.add(textComposite, 0);

        Assert.assertEquals(1, completedTextRepository.getData().size());
        Assert.assertEquals(0, sentenceRepository.getData().size());
    }

    @Test
    public void compile() {
        CompletedTextComposite textComposite = controller.parse(text);
        controller.addAll(textComposite, 0);
        List<CompletedTextComposite> texts = controller.compile();
        int textLength = texts.get(0).getText().split(" ").length;
        Assert.assertEquals(48, textLength);
    }

}
