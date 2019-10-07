package parserchain;

public interface ParserChain<T> {
    T parse(String text);
    void linkWith(ParserChain<T> next);
}
