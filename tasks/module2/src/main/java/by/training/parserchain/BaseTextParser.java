package by.training.parserchain;

import by.training.model.TextLeaf;

public abstract class BaseTextParser implements ParserChain<TextLeaf> {

    private BaseTextParser next;

    @Override
    public void linkWith(ParserChain<TextLeaf> next) {
        this.next = (BaseTextParser) next;
    }

    protected TextLeaf nextParse(String text) {
        if (next != null) {
            return next.parse(text);
        }

        return null;
    }
}
