package by.training.parserchain;

import by.training.model.ParagraphComposite;
import by.training.model.TextComposite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SentenceParserTest {

    private String paragraph;
    ;

    @Before
    public void init() {

        paragraph = "\tIt has survived not only five centuries, but also the leap into electronic " +
                "typesetting, remaining essentially unchanged. It was popularised in the with the " +
                "release of Letraset sheets containing Lorem Ipsum passages, and more recently with " +
                "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. ";

    }

    @Test
    public void parseParagraphToSentences() {

        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();

        sentenceParser.linkWith(wordParser);

        TextComposite composite = (ParagraphComposite) sentenceParser.parse(paragraph);

        Assert.assertEquals(paragraph, composite.getText());
    }

}
