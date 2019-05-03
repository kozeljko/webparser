package si.fri.kozelj.regex;

public abstract class AbstractRegexParser {
    private final String pageContent;

    public AbstractRegexParser(String pageContent) {
        this.pageContent = pageContent;
    }

    public abstract String parseJson();
}
