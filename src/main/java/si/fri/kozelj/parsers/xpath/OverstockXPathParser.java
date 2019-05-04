package si.fri.kozelj.parsers.xpath;

import si.fri.kozelj.models.OverstockItem;
import si.fri.kozelj.models.OverstockModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OverstockXPathParser extends AbstractXPathParser {
    /**
     * Note, that the first "tr" tag is important, because we select the 2nd child of it, which then excludes all "tr"
     * tags with only one child (these 1 child tags surround the content tr tags).
     */
    private static final String TITLE_XPATH_EXPRESSION = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td/a/b/text()";
    private static final String LIST_PRICE_XPATH_EXPRESSION = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/s/text()";
    private static final String PRICE_XPATH_EXPRESSION = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/span/b/text()";
    private static final String SAVING_XPATH_EXPRESSION = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/span/text()";
    private static final String SAVING_PERCENT_XPATH_EXPRESSION = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/span/text()";
    private static final String CONTENT_XPATH_EXPRESSION = "//table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr/td[2]/span/text()";

    /**
     * Example value: $124.4 (40%)
     *
     * Saving is first part, saving percent is second part.
     */
    private static final Pattern SAVING_PATTERN = Pattern.compile("(.*?)\\s\\(.*?\\)");
    private static final Pattern SAVING_PERCENT_PATTERN = Pattern.compile(".*?(\\(.*?\\))");

    public OverstockXPathParser(String pageContent) {
        super(pageContent);
    }

    @Override
    public String parseJson() {
        List<String> titles = getMatches(TITLE_XPATH_EXPRESSION);
        List<String> listPrices= getMatches(LIST_PRICE_XPATH_EXPRESSION);
        List<String> prices = getMatches(PRICE_XPATH_EXPRESSION);
        List<String> savings = getMatches(SAVING_XPATH_EXPRESSION, SAVING_PATTERN);
        List<String> savingPercents = getMatches(SAVING_PERCENT_XPATH_EXPRESSION, SAVING_PERCENT_PATTERN);
        List<String> contents = getMatches(CONTENT_XPATH_EXPRESSION);

        List<OverstockItem> items = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            OverstockItem item = new OverstockItem.Builder()
                    .title(titles.get(i))
                    .listPrice(listPrices.get(i))
                    .price(prices.get(i))
                    .saving(savings.get(i))
                    .savingPercent(savingPercents.get(i))
                    .content(contents.get(i))
                    .build();

            items.add(item);
        }

        return getGson().toJson(new OverstockModel(items));
    }
}
