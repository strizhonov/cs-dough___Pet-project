package by.training.controller;

import by.training.model.CompletedTextComposite;
import by.training.model.SentenceComposite;
import by.training.model.TextComposite;
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
import java.util.List;

;

@RunWith(JUnit4.class)
public class TextControllerTest {

    private TextController controller;

    @Before
    public void init() {

        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();
        FileValidator fileValidator = new FileValidator();
        FileReader fileReader = new FileReaderImpl();

        WordRepository wordRepository = new WordRepository();
        SentenceRepository sentenceRepository = new SentenceRepository(wordRepository);
        ParagraphRepository paragraphRepository = new ParagraphRepository(sentenceRepository);
        CompletedTextRepository completedTextRepository = new CompletedTextRepository(paragraphRepository);

        WordService wordService = new WordService(wordRepository);
        SentenceService sentenceService = new SentenceService(sentenceRepository, wordService);
        ParagraphService paragraphService = new ParagraphService(paragraphRepository, sentenceService);
        CompletedTextService textService = new CompletedTextService(completedTextRepository, paragraphService);

        controller = new TextController(textService, paragraphParser,
                sentenceParser, wordParser, fileValidator, fileReader);

    }

    @Test
    public void proceedFile() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        controller.proceedFile(path);

        List<SentenceComposite> sentences = controller.sortSentencesByWords(true);

        Assert.assertTrue(sentences.get(0).getText().length() < sentences.get(5).getText().length());

    }

    @Test
    public void compile() throws URISyntaxException, IOException {
        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        controller.proceedFile(path);
        List<CompletedTextComposite> texts = controller.compile();
        int textLength = texts.get(0).getText().split(" ").length;
        Assert.assertEquals(120, textLength);
    }

    @Test
    public void sortAsc() throws URISyntaxException, IOException {

        URI uri = this.getClass().getResource("/valid_text.txt").toURI();
        String path = Paths.get(uri).toString();

        controller.proceedFile(path);

        List<CompletedTextComposite> texts = controller.compile();
        CompletedTextComposite text = texts.get(0);
        text.sortChildrenByLeavesCount(true);
        int firstSentenceLength = text.getAll().get(0).getText().length();
        int lastSentenceLength = text.getAll().get(3).getText().length();
        Assert.assertTrue(firstSentenceLength < lastSentenceLength);

    }


}
