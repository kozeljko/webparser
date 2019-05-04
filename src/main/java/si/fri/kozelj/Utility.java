package si.fri.kozelj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String cleanRtvContent(String rawString) {
        rawString = rawString.replaceAll("<\\/?article.*?>", "");

        Matcher matcher = Pattern.compile("<p.*?>([\\s\\S\\r]*?)<\\/p>").matcher(rawString);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            String rawSubstring = rawString.substring(matcher.start(1), matcher.end(1));
            builder.append(rawSubstring);
        }

        return builder.toString().replaceAll("<iframe.*?\\/iframe>", "")
                      .replaceAll("[\\t\\r\\n]*", "")
                      .replaceAll("<strong>", " ") // specific replace, where we replace the start of a title for a nice display
                      .replaceAll("<\\/strong>(?:<br>|\\s)-", ": ") // specific replace, where we replace the ending of a title for nice display
                      .replaceAll("- ", ", ") // replace - with a comma, for nicer display
                      .replaceAll("<\\/?.*?>", "") // remove all remaining tags (like <b>
                      .replaceAll("\\s+", " "); // shorten multiple whitespace occurrences
    }

}
