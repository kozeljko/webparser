package si.fri.kozelj.parsers.regex;

import si.fri.kozelj.Utility;
import si.fri.kozelj.models.RtvModel;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RtvRegexParser extends AbstractRegexParser {
    private static final Pattern AUTHOR_PATTERN = Pattern.compile("<div class=\"author-name\">(.*?)<\\/div>");
    private static final Pattern PUBLISHED_TIME_PATTERN = Pattern.compile("<div class=\"publish-meta\">(?:.|\\r|\\n)*?([^\\t]*?)<br>");
    private static final Pattern TITLE_PATTERN = Pattern.compile("<h1>(.*)<\\/h1>");
    private static final Pattern SUBTITLE_PATTERN = Pattern.compile("<div class=\"subtitle\">(.*)<\\/div>");
    private static final Pattern LEAD_PATTERN = Pattern.compile("<p class=\"lead\">(.*)<\\/p>");
    private static final Pattern CONTENT_PATTERN = Pattern.compile("<article class=\"article\">([\\s\\S\\r]*)<\\/article>", Pattern.DOTALL);

    public RtvRegexParser(String pageContent) {
        super(pageContent);
    }

    @Override
    public String parseJson() {
        List<String> authors = getMatches(AUTHOR_PATTERN, true);
        List<String> publishedTimes = getMatches(PUBLISHED_TIME_PATTERN, true);
        List<String> titles = getMatches(TITLE_PATTERN, true);
        List<String> subtitles = getMatches(SUBTITLE_PATTERN, true);
        List<String> leads = getMatches(LEAD_PATTERN, true);
        List<String> contents = getMatches(CONTENT_PATTERN, false).stream().map(Utility::cleanRtvContent).collect(Collectors.toList());

        RtvModel model = new RtvModel.Builder()
                .author(authors.get(0))
                .publishedTime(publishedTimes.get(0))
                .title(titles.get(0))
                .subTitle(subtitles.get(0))
                .lead(leads.get(0))
                .content(contents.get(0))
                .build();

        return getGson().toJson(model);
    }
}
