package si.fri.kozelj.parsers.xpath;

import si.fri.kozelj.Utility;
import si.fri.kozelj.models.RtvModel;

import java.util.List;
import java.util.stream.Collectors;

public class RtvXPathParser extends AbstractXPathParser {
    private static final String AUTHOR_XPATH_EXPRESSION = "//div[@class='author-name']/text()";
    private static final String PUBLISHED_TIME_XPATH_EXPRESSION = "//div[@class='publish-meta']/text()[1]";
    private static final String TITLE_XPATH_EXPRESSION = "//h1/text()";
    private static final String SUBTITLE_XPATH_EXPRESSION = "//div[@class='subtitle']/text()";
    private static final String LEAD_XPATH_EXPRESSION = "//p[@class='lead']/text()";
    private static final String CONTENT_XPATH_EXPRESSION = "//article";
    
    public RtvXPathParser(String pageContent) {
        super(pageContent);
    }

    @Override
    public String parseJson() {
        List<String> authors = getMatches(AUTHOR_XPATH_EXPRESSION);
        List<String> publishedTimes = getMatches(PUBLISHED_TIME_XPATH_EXPRESSION);
        List<String> titles = getMatches(TITLE_XPATH_EXPRESSION);
        List<String> subtitles = getMatches(SUBTITLE_XPATH_EXPRESSION);
        List<String> leads = getMatches(LEAD_XPATH_EXPRESSION);
        List<String> contents = getRawMatches(CONTENT_XPATH_EXPRESSION).stream().map(Utility::cleanRtvContent).collect(Collectors.toList());

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
