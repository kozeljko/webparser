package si.fri.kozelj.regex;

public class RtvRegexParser extends AbstractRegexParser {
    public RtvRegexParser(String pageContent) {
        super(pageContent);
        System.out.println(pageContent);
    }

    @Override
    public String parseJson() {
        return null;
    }
}
