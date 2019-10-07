package parserchain;

import model.SentenceComposite;
import model.TextComposite;
import model.TextLeaf;
import model.WordLeaf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends BaseTextParser {

    private static final String WORD_REGEX = "([\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\\"\\']+)?([\\w\\-\\']+)([\\.\\!\\?\\,\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\\"\\']+)?";

    @Override
    public TextLeaf parse(String text) {
        TextComposite composite = new SentenceComposite();

        Pattern pattern = Pattern.compile(WORD_REGEX);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String before = matcher.group(1);
            String word = matcher.group(2);
            String after = matcher.group(3);

            if (before == null) {
                before = "";
            }
            if (after == null) {
                after = "";
            }

            TextLeaf leaf = new WordLeaf(before, word, after);
            composite.add(leaf);
        }
        return composite;
    }

}
