package si.fri.kozelj.parsers.xpath;

import si.fri.kozelj.models.BooksItem;
import si.fri.kozelj.models.BooksModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BookXPathParser extends AbstractXPathParser {
    private static final String TITLE_XPATH_EXPRESSION = "//a[@class='bookTitle']/span/text()";
    private static final String AUTHOR_XPATH_EXPRESSION = "//a[@class='authorName']/span/text()";
    private static final String AVG_RATING_XPATH_EXPRESSION = "//span[@class='minirating']/text()[2]";
    private static final String RATING_XPATH_EXPRESSION = "//span[@class='minirating']/text()[2]";
    private static final String SCORE_XPATH_EXPRESSION = "//a[@class='bookTitle']/following-sibling::div[2]/span/a[1]/text()";
    private static final String VOTE_XPATH_EXPRESSION = "//a[@class='bookTitle']/following-sibling::div[2]/span/a[2]/text()";

    private static final Pattern AVG_RATING_PATTERN = Pattern.compile("(.*?) avg rating");
    private static final Pattern RATING_PATTERN = Pattern.compile("avg rating.*?(\\d+.*?) ratings");
    private static final Pattern SCORE_PATTERN = Pattern.compile("score: (.*)$");
    private static final Pattern VOTE_PATTERN = Pattern.compile("^(.*?) people voted");

    public BookXPathParser(String pageContent) {
        super(pageContent);
    }

    @Override
    public String parseJson() {
        List<String> titles = getMatches(TITLE_XPATH_EXPRESSION);
        List<String> authors = getMatches(AUTHOR_XPATH_EXPRESSION);
        List<String> avgRatings = getMatches(AVG_RATING_XPATH_EXPRESSION, AVG_RATING_PATTERN);
        List<String> ratings = getMatches(RATING_XPATH_EXPRESSION, RATING_PATTERN);
        List<String> scores = getMatches(SCORE_XPATH_EXPRESSION, SCORE_PATTERN);
        List<String> votes = getMatches(VOTE_XPATH_EXPRESSION, VOTE_PATTERN);

        List<BooksItem> booksItems = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            BooksItem booksItem = new BooksItem.Builder()
                    .title(titles.get(i))
                    .author(authors.get(i))
                    .avgRating(avgRatings.get(i))
                    .ratings(ratings.get(i))
                    .score(scores.get(i))
                    .votes(votes.get(i))
                    .build();

            booksItems.add(booksItem);
        }

        return getGson().toJson(new BooksModel(booksItems));
    }
}
