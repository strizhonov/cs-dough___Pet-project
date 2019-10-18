package by.training.controller;

import by.training.model.CompletedTextComposite;
import by.training.model.ParagraphComposite;
import by.training.parserchain.ParagraphParser;
import by.training.parserchain.SentenceParser;
import by.training.parserchain.WordParser;
import by.training.reader.FileReader;
import by.training.reader.FileReaderImpl;
import by.training.repository.CompletedTextRepository;
import by.training.repository.ParagraphRepository;
import by.training.repository.SentenceRepository;
import by.training.repository.WordRepository;
import by.training.service.CompletedTextService;
import by.training.service.ParagraphService;
import by.training.service.SentenceService;
import by.training.service.WordService;
import by.training.validator.FileValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;


@RunWith(JUnit4.class)
public class TextControllerTest {

    private TextController controller;
    private WordRepository wordRepository;
    private SentenceRepository sentenceRepository;
    private ParagraphRepository paragraphRepository;
    private CompletedTextRepository completedTextRepository;
    private ParagraphService paragraphService;
    private WordService wordService;
    private SentenceService sentenceService;

    @Before
    public void init() {

        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();
        FileValidator fileValidator = new FileValidator();
        FileReader fileReader = new FileReaderImpl();

        wordRepository = new WordRepository();
        sentenceRepository = new SentenceRepository();
        paragraphRepository = new ParagraphRepository();
        completedTextRepository = new CompletedTextRepository();

        wordService = new WordService(wordRepository);
        sentenceService = new SentenceService(sentenceRepository);
        paragraphService = new ParagraphService(paragraphRepository);
        CompletedTextService textService = new CompletedTextService(completedTextRepository);

        controller = new TextController(textService, paragraphService, sentenceService, wordService, paragraphParser,
                sentenceParser, wordParser, fileValidator, fileReader);

    }

    @Test
    public void proceedFile() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        String text = controller.proceedFile(path);
        int expectedLength = text.split(" ").length;
        Assert.assertEquals(116, expectedLength);
    }

    @Test
    public void save() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        String text = controller.proceedFile(path);

        CompletedTextComposite composite = controller.compile(text);

        controller.save(composite);

        int texts = completedTextRepository.getData().size();
        int paragraphs = paragraphRepository.getData().size();
        int sentences = sentenceRepository.getData().size();
        int words = wordRepository.getData().size();
        Assert.assertEquals(1, texts);
        Assert.assertEquals(4, paragraphs);
        Assert.assertEquals(6, sentences);
        Assert.assertEquals(119, words);
    }

    @Test
    public void sortAsc() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        String text = controller.proceedFile(path);

        CompletedTextComposite composite = controller.compile(text);

        controller.save(composite);

        ParagraphComposite paragraphComposite = new ParagraphComposite();
        paragraphComposite.setService(paragraphService);
        paragraphComposite.setChildService(sentenceService);
        paragraphComposite.setWordService(wordService);

        paragraphComposite.load(2);

        paragraphComposite.sortChildrenByLeavesCount(false);
        int firstSentenceLength = paragraphComposite.getAll().get(0).getText().split(" ").length;
        int secondSentenceLength = paragraphComposite.getAll().get(1).getText().split(" ").length;

        Assert.assertTrue(firstSentenceLength > secondSentenceLength);
    }

}
