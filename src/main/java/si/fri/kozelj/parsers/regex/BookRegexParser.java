package si.fri.kozelj.parsers.regex;

import si.fri.kozelj.models.BooksItem;
import si.fri.kozelj.models.BooksModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BookRegexParser extends AbstractRegexParser {
    private static final Pattern TITLE_PATTERN = Pattern.compile("<a class=\"bookTitle\" .*?>[\\s\\S\\r]*?<span .*?>(.*?)<\\/span>");
    private static final Pattern AUTHOR_PATTERN = Pattern.compile("<a class=\"authorName\" .*?><span .*?>(.*?)<\\/span>");
    private static final Pattern AVG_RATING_PATTERN = Pattern.compile("([\\d\\.]+)\\savg rating");
    private static final Pattern RATING_PATTERN = Pattern.compile("avg rating [^\\d]*(.*?) ratings");
    private static final Pattern SCORE_PATTERN = Pattern.compile("<a .*?>score: (.*?)<\\/a>");
    private static final Pattern VOTE_PATTERN = Pattern.compile("<a .*?>(.*?) people voted<\\/a>");

    public BookRegexParser(String pageContent) {
        super(pageContent);
    }

    @Override
    public String parseJson() {
        List<String> titles = getMatches(TITLE_PATTERN, true);
        List<String> authors = getMatches(AUTHOR_PATTERN, true);
        List<String> avgRatings = getMatches(AVG_RATING_PATTERN, true);
        List<String> ratings = getMatches(RATING_PATTERN, true);
        List<String> scores = getMatches(SCORE_PATTERN, true);
        List<String> votes = getMatches(VOTE_PATTERN, true);

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
