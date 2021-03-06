package si.fri.kozelj.parsers.regex;

import com.google.gson.Gson;
import si.fri.kozelj.parsers.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRegexParser implements Parser {
    private final String pageContent;
    private final Gson gson;

    AbstractRegexParser(String pageContent) {
        this.pageContent = pageContent;
        this.gson = new Gson();
    }

    List<String> getMatches(Pattern pattern, boolean cleanString) {
        Matcher matcher = pattern.matcher(pageContent);
        List<String> foundMatches = new ArrayList<>();
        while (matcher.find()) {
            String rawSubstring = pageContent.substring(matcher.start(1), matcher.end(1));
            foundMatches.add(cleanString ? cleanMatch(rawSubstring) : rawSubstring);
        }

        return foundMatches;
    }

    Gson getGson() {
        return gson;
    }
}
