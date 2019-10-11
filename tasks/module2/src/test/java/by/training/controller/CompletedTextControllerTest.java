package by.training.controller;

import by.training.model.CompletedTextComposite;;
import by.training.model.SentenceComposite;
import by.training.reader.FileReader;
import by.training.reader.FileReaderImpl;
import by.training.validator.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import by.training.parserchain.ParagraphParser;
import by.training.parserchain.SentenceParser;
import by.training.parserchain.WordParser;
import by.training.repository.CompletedTextRepository;
import by.training.repository.ParagraphRepository;
import by.training.repository.SentenceRepository;
import by.training.repository.WordRepository;
import by.training.service.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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
        FileValidator fileValidator = new FileValidator();
        FileReader fileReader = new FileReaderImpl();

        WordRepository wordRepository = new WordRepository();
        sentenceRepository = new SentenceRepository(wordRepository);
        ParagraphRepository paragraphRepository = new ParagraphRepository(sentenceRepository);
        completedTextRepository = new CompletedTextRepository(paragraphRepository);

        WordService wordService = new WordService(wordRepository);
        SentenceService sentenceService = new SentenceService(sentenceRepository, wordService);
        ParagraphService paragraphService = new ParagraphService(paragraphRepository, sentenceService);
        CompletedTextService textService = new CompletedTextService(completedTextRepository, paragraphService);

        WordController wordController = new WordController(wordService);
        SentenceController sentenceController = new SentenceController(sentenceService, wordController);
        ParagraphController paragraphController = new ParagraphController(paragraphService, sentenceController);

        controller = new CompletedTextController(textService, paragraphController, paragraphParser,
                sentenceParser, wordParser, fileValidator, fileReader);

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

    @Test
    public void sortAsc() {

        CompletedTextComposite textComposite = controller.parse(text);
        controller.addAll(textComposite, 0);

        List<SentenceComposite> sentences = controller.sortSentencesByWords(true);
        Assert.assertTrue(sentences.get(0).getText().length() < sentences.get(1).getText().length());

    }
    @Test
    public void sortDesc() {

        CompletedTextComposite textComposite = controller.parse(text);
        controller.addAll(textComposite, 0);

        List<SentenceComposite> sentences = controller.sortSentencesByWords(false);
        Assert.assertTrue(sentences.get(0).getText().length() > sentences.get(1).getText().length());
    }

    @Test
    public void pathCheck() throws URISyntaxException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        Assert.assertTrue(controller.isPathValid(path));
    }

    @Test
    public void execute() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        String text = controller.read(path);
        CompletedTextComposite textComposite = controller.parse(text);

        controller.addAll(textComposite, 0);

        List<SentenceComposite> sentences = controller.sortSentencesByWords(true);

        Assert.assertTrue(sentences.get(0).getText().length() < sentences.get(5).getText().length());

    }
}
