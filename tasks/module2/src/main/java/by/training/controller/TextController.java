package by.training.controller;

import by.training.model.CompletedTextComposite;
import by.training.model.SentenceComposite;
import by.training.parserchain.ParagraphParser;
import by.training.parserchain.SentenceParser;
import by.training.parserchain.WordParser;
import by.training.reader.FileReader;
import by.training.service.CompletedTextService;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;

import java.io.IOException;
import java.util.List;

public class TextController {

    private CompletedTextService service;
    private ParagraphParser paragraphParser;
    private SentenceParser sentenceParser;
    private WordParser wordParser;
    private FileValidator fileValidator;
    private FileReader fileReader;

    public TextController(CompletedTextService service,
                          ParagraphParser paragraphParser,
                          SentenceParser sentenceParser,
                          WordParser wordParser,
                          FileValidator fileValidator,
                          FileReader fileReader) {
        this.service = service;
        this.paragraphParser = paragraphParser;
        this.sentenceParser = sentenceParser;
        this.wordParser = wordParser;
        this.fileValidator = fileValidator;
        this.fileReader = fileReader;
    }

    public List<CompletedTextComposite> compile() {
        return service.compile();
    }

    public List<SentenceComposite> sortSentencesByWords(boolean asc) {
        return service.sortSentencesByWords(asc);
    }

    public void proceedFile(String filePath) throws IOException {
        ValidationResult validationResult = fileValidator.validate(filePath);
        if (!validationResult.isValid()) {
            throw new IOException();
        }

        paragraphParser.linkWith(sentenceParser);
        sentenceParser.linkWith(wordParser);

        String text = fileReader.read(filePath);
        CompletedTextComposite composite = (CompletedTextComposite) paragraphParser.parse(text);

        service.addAll(composite, 0);
    }


}
