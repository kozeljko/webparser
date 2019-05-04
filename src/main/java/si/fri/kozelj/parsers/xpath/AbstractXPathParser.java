package si.fri.kozelj.parsers.xpath;

import com.google.gson.Gson;
import org.htmlcleaner.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import si.fri.kozelj.parsers.Parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
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
            /**
             * Clean markup and save into string. We do this step, in order to preserve UTF-8 encoding, while
             * building the Document object.
             */
            CleanerProperties cleanerProperties = new CleanerProperties();
            cleanerProperties.setCharset("UTF-8");
            String cleanedHtmlContent = new PrettyXmlSerializer(cleanerProperties).getAsString(tagNode);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            pageDocument = dBuilder.parse(new ByteArrayInputStream(cleanedHtmlContent.getBytes(Charset.forName("UTF-8"))));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Error while cleaning markup");
        }
    }

    private NodeList getNodeList(String expression) {
        NodeList nodeList;
        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(pageDocument, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Error while evaluation expression: " + expression);
        }

        return nodeList;
    }

    List<String> getRawMatches(String expression) {
        NodeList nodeList = getNodeList(expression);

        List<String> foundMatches = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            String nodeValue = getNodeString(nodeList.item(i));
            foundMatches.add(nodeValue);
        }

        return foundMatches;
    }

    List<String> getMatches(String expression) {
        NodeList nodeList = getNodeList(expression);

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
        return match.replaceAll("\\n", " ").trim();
    }

    String getNodeString(Node node) {
        try {
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            String output = writer.toString();
            return output.substring(output.indexOf("?>") + 2);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return node.getTextContent();
    }
}
