package by.training.controller;

import by.training.model.CompletedTextComposite;
import by.training.model.ParagraphComposite;
import by.training.model.SentenceComposite;
import by.training.model.TextLeaf;
import by.training.parserchain.ParagraphParser;
import by.training.parserchain.SentenceParser;
import by.training.parserchain.WordParser;
import by.training.reader.FileReader;
import by.training.service.CompletedTextService;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class CompletedTextController {

    private static final Logger LOGGER = Logger.getLogger(CompletedTextController.class);
    private CompletedTextService service;
    private ParagraphController childController;
    private ParagraphParser paragraphParser;
    private SentenceParser sentenceParser;
    private WordParser wordParser;
    private FileValidator fileValidator;
    private FileReader fileReader;

    public CompletedTextController(CompletedTextService service,
                                   ParagraphController childController,
                                   ParagraphParser paragraphParser,
                                   SentenceParser sentenceParser,
                                   WordParser wordParser,
                                   FileValidator fileValidator,
                                   FileReader fileReader) {
        this.service = service;
        this.childController = childController;
        this.paragraphParser = paragraphParser;
        this.sentenceParser = sentenceParser;
        this.wordParser = wordParser;
        this.fileValidator = fileValidator;
        this.fileReader = fileReader;
    }

    public boolean isPathValid(String path) {
        ValidationResult validationResult = fileValidator.validate(path);
        return validationResult.isValid();
    }

    public String read(String path) throws IOException {
        return fileReader.read(path);
    }

    public long add(CompletedTextComposite item, long parentId) {
        return service.add(item, parentId);
    }

    public void addAll(CompletedTextComposite item, long parentId) {
        long id = add(item, 0);
        for (TextLeaf leaf : item.getAll()) {
            childController.addAll((ParagraphComposite) leaf, id);
        }
    }

    public CompletedTextComposite parse(String text) {
        paragraphParser.linkWith(sentenceParser);
        sentenceParser.linkWith(wordParser);

        CompletedTextComposite composite = (CompletedTextComposite) paragraphParser.parse(text);
        LOGGER.info("Text successfully parsed");

        return composite;
    }

    public List<CompletedTextComposite> compile() {
        return service.compile();
    }

    public List<SentenceComposite> sortSentencesByWords(boolean asc) {
        return childController.sortSentencesByWords(asc);
    }


}
