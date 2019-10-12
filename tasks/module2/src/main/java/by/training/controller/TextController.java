package by.training.controller;

import by.training.model.CompletedTextComposite;
import by.training.parserchain.ParagraphParser;
import by.training.parserchain.SentenceParser;
import by.training.parserchain.WordParser;
import by.training.reader.FileReader;
import by.training.service.CompletedTextService;
import by.training.service.ParagraphService;
import by.training.service.SentenceService;
import by.training.service.WordService;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;

import java.io.IOException;

public class TextController {

    private CompletedTextService completedTextService;
    private ParagraphService paragraphService;
    private SentenceService sentenceService;
    private WordService wordService;
    private ParagraphParser paragraphParser;
    private SentenceParser sentenceParser;
    private WordParser wordParser;
    private FileValidator fileValidator;
    private FileReader fileReader;

    public TextController(CompletedTextService completedTextService,
                          ParagraphService paragraphService,
                          SentenceService sentenceService,
                          WordService wordService,
                          ParagraphParser paragraphParser,
                          SentenceParser sentenceParser,
                          WordParser wordParser,
                          FileValidator fileValidator,
                          FileReader fileReader) {
        this.completedTextService = completedTextService;
        this.paragraphService = paragraphService;
        this.sentenceService = sentenceService;
        this.wordService = wordService;
        this.paragraphParser = paragraphParser;
        this.sentenceParser = sentenceParser;
        this.wordParser = wordParser;
        this.fileValidator = fileValidator;
        this.fileReader = fileReader;
    }

    public String proceedFile(String filePath) throws IOException {
        ValidationResult validationResult = fileValidator.validate(filePath);
        if (!validationResult.isValid()) {
            throw new IOException();
        }

        paragraphParser.linkWith(sentenceParser);
        sentenceParser.linkWith(wordParser);

        return fileReader.read(filePath);
    }

    public CompletedTextComposite compile(String text) {
        return (CompletedTextComposite) paragraphParser.parse(text);
    }

    public void save(CompletedTextComposite composite) {
        composite.setService(completedTextService);
        composite.setChildService(paragraphService);
        composite.setSentenceService(sentenceService);
        composite.setWordService(wordService);

        composite.save(-1);
    }


}
