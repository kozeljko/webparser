package si.fri.kozelj.regex;

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
        List<String> titles = getMatches(TITLE_PATTERN, true);
        List<String> listPrices = getMatches(LIST_PRICE_PATTERN, true);
        List<String> prices = getMatches(PRICE_PATTERN, true);
        List<String> savings = getMatches(SAVING_PATTERN, true);
        List<String> savingPercentages = getMatches(SAVING_PERCENT_PATTERN, true);
        List<String> contents = getMatches(CONTENT_PATTERN, true);

        List<OverstockItem> models = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            OverstockItem item = new OverstockItem.Builder()
                    .title(titles.get(i))
                    .listPrice(listPrices.get(i))
                    .price(prices.get(i))
                    .saving(savings.get(i))
                    .savingPercent(savingPercentages.get(i))
                    .content(contents.get(i))
                    .build();

            models.add(item);
        }

        return getGson().toJson(new OverstockModel(models));
    }
}
