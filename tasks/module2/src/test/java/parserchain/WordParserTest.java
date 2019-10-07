package parserchain;

import model.SentenceComposite;
import model.TextComposite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WordParserTest {

    private String sentence;

    @Before
    public void init() {
        sentence  = "It is a established fact that a reader will be of a page when looking at its layout... ";
    }

    @Test
    public void parseSentenceToWords() {
        WordParser wordParser = new WordParser();
        TextComposite composite = (SentenceComposite) wordParser.parse(sentence);
        Assert.assertEquals(18, composite.getAll().size());
    }

}
