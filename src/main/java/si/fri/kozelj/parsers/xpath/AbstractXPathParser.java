package si.fri.kozelj.parsers.xpath;

import com.google.gson.Gson;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import si.fri.kozelj.parsers.Parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractXPathParser implements Parser {
    private final Document pageDocument;
    private final Gson gson;
    private final XPath xPath;

    public AbstractXPathParser(String pageContent) {
        this.gson = new Gson();
        this.xPath = XPathFactory.newInstance().newXPath();

        TagNode tagNode = new HtmlCleaner().clean(pageContent);
        try {
            this.pageDocument = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Error while cleaning markup");
        }
    }

    List<String> getMatches(String expression) {
        NodeList nodeList;
        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(pageDocument, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Error while evaluation expression: " + expression);
        }

        List<String> foundMatches = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            String nodeValue = nodeList.item(i).getNodeValue();
            foundMatches.add(cleanMatch(nodeValue));
        }

        return foundMatches;
    }

    List<String> getMatches(String expression, Pattern pattern) {
        List<String> foundMatches = getMatches(expression);
        List<String> transformedMatches = new ArrayList<>();

        for (String match : foundMatches) {
            Matcher matcher = pattern.matcher(match);

            if (matcher.find()) {
                transformedMatches.add(match.substring(matcher.start(1), matcher.end(1)));
            } else {
                throw new RuntimeException("Error while trying to apply pattern: " + pattern + " to match: " + match);
            }
        }

        return transformedMatches;
    }

    Gson getGson() {
        return gson;
    }

    private String cleanMatch(String match) {
        return match.replaceAll("\\n", " ");
    }
}
