package by.training.parserchain;

import by.training.model.CompletedTextComposite;
import by.training.model.TextComposite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ParserChainTest {

    private String text;

    @Before
    public void init() {
        text = "\tIt has survived not only five centuries, but also the leap into electronic " +
                "typesetting, remaining essentially unchanged. It was popularised in the with the " +
                "release of Letraset sheets containing Lorem Ipsum passages, and more recently with " +
                "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. \n" +
                "\tIt is a long established fact that a reader will be distracted by the readable " +
                "content of a page when looking at its layout. The point of using Ipsum is that it has a " +
                "more-or-less normal distribution of letters, as opposed to using 'Content here, content" +
                "here', making it look like readable English. \n" +
                "\tIt is a established fact that a reader will be of a page when looking at its " +
                "layout... \n" +
                "\tBye. \n";
    }

    @Test
    public void parseText() {

        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        WordParser wordParser = new WordParser();

        paragraphParser.linkWith(sentenceParser);
        sentenceParser.linkWith(wordParser);

        TextComposite composite = (CompletedTextComposite) paragraphParser.parse(text);

        Assert.assertEquals(text, composite.getText());

    }




}
