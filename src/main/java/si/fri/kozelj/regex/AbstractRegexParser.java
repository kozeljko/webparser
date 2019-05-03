package si.fri.kozelj.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRegexParser {
    protected final String pageContent;

    public AbstractRegexParser(String pageContent) {
        this.pageContent = pageContent;
    }

    public abstract String parseJson();

    protected List<String> getMatches(Pattern pattern) {
        Matcher matcher = pattern.matcher(pageContent);
        List<String> foundMatches = new ArrayList<>();
        while (matcher.find()) {
            foundMatches.add(pageContent.substring(matcher.start(1), matcher.end(1)));
        }

        return foundMatches;
    }

//    private String cleanMatch(String match) {
//        return match.replaceAll("<\\/?.*?>", "");
//    }
}
