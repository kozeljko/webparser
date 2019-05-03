package si.fri.kozelj.regex;

import com.google.gson.Gson;
import si.fri.kozelj.models.OverstockItem;
import si.fri.kozelj.models.OverstockModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OverstockRegexParser extends AbstractRegexParser {
    private static final Pattern TITLE_PATTERN = Pattern.compile("<a .*?>(.*)<\\/a><br>\\s[\\r\\n]*<table>");
    private static final Pattern LIST_PRICE_PATTERN = Pattern.compile("List Price:<\\/b><\\/td><td .*?>(.*)<\\/td>");
    private static final Pattern PRICE_PATTERN = Pattern.compile("[^\\s]Price:<\\/b><\\/td><td .*?><span .*?>(.*)<\\/span><\\/td>");
    private static final Pattern SAVING_PATTERN = Pattern.compile("You Save:<\\/b><\\/td><td .*?><span .*?>(.*) \\(\\d+%\\)<\\/span><\\/td>");
    private static final Pattern SAVING_PERCENT_PATTERN = Pattern.compile("You Save:<\\/b><\\/td><td .*?><span .*?>.+? (\\(\\d+%\\))<\\/span><\\/td>");
    private static final Pattern CONTENT_PATTERN = Pattern.compile("You Save:.*[\\r\\n]+.*[\\r\\n]+.*<td .*?><span .*?>((.|\\r|\\n)*?)<\\/span><br>");

    public OverstockRegexParser(String pageContent) {
        super(pageContent);
    }

    @Override
    public String parseJson() {
        List<String> titles = getMatches(TITLE_PATTERN);
        List<String> listPrices = getMatches(LIST_PRICE_PATTERN);
        List<String> prices = getMatches(PRICE_PATTERN);
        List<String> savings = getMatches(SAVING_PATTERN);
        List<String> savingPercentages = getMatches(SAVING_PERCENT_PATTERN);
        List<String> contents = getMatches(CONTENT_PATTERN);

        List<OverstockItem> models = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            OverstockItem.Builder builder = new OverstockItem.Builder();
            builder.title(titles.get(i));
            builder.listPrice(listPrices.get(i));
            builder.price(prices.get(i));
            builder.saving(savings.get(i));
            builder.savingPercent(savingPercentages.get(i));
            builder.content(contents.get(i));

            models.add(builder.build());
        }

        OverstockModel model = new OverstockModel(models);
        Gson gson = new Gson();

        return gson.toJson(model);
    }
}
