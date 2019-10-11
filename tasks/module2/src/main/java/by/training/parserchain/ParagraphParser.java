package by.training.parserchain;

import by.training.model.CompletedTextComposite;
import by.training.model.TextComposite;
import by.training.model.TextLeaf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends BaseTextParser {

    private static final String PARAGRAPH_REGEX = "(\\t)(.+)(\\n)?";

    @Override
    public TextLeaf parse(String text) {
        TextComposite composite = new CompletedTextComposite();

        Pattern pattern = Pattern.compile(PARAGRAPH_REGEX);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String sParagraph = matcher.group();
            TextLeaf tlParagraph = nextParse(sParagraph);
            composite.add(tlParagraph);
        }

        return composite;
    }
}
