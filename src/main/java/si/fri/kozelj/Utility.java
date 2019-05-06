package si.fri.kozelj;

import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
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

    public static String getNodeString(Node node) {
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
