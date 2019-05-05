package si.fri.kozelj.parsers;

import si.fri.kozelj.parsers.regex.BookRegexParser;
import si.fri.kozelj.parsers.regex.OverstockRegexParser;
import si.fri.kozelj.parsers.regex.RtvRegexParser;
import si.fri.kozelj.parsers.xpath.BookXPathParser;
import si.fri.kozelj.parsers.xpath.OverstockXPathParser;
import si.fri.kozelj.parsers.xpath.RtvXPathParser;

import java.util.Arrays;

public class ParserFactory {
    private final String method;
    private final String fileName;

    public ParserFactory(String method, String fileName) {
        this.method = method;
        this.fileName = fileName;
    }

    public Parser getParser(String fileContent) {
        switch (method) {
            case "regex":
            case "regex-via-path":
                return getRegexParser(fileContent);
            case "xpath":
            case "xpath-via-path":
                return getXPathParser(fileContent);
            default:
                throw new RuntimeException("Unknown method");
        }
    }

    private Parser getRegexParser(String fileContent) {
        switch (PageType.getType(fileName)) {
            case OVERSTOCK:
                return new OverstockRegexParser(fileContent);
            case RTV:
                return new RtvRegexParser(fileContent);
            case BOOKS:
                return new BookRegexParser(fileContent);
            default:
                throw new RuntimeException("Unknown file name");
        }
    }

    private Parser getXPathParser(String fileContent) {
        switch (PageType.getType(fileName)) {
            case OVERSTOCK:
                return new OverstockXPathParser(fileContent);
            case RTV:
                return new RtvXPathParser(fileContent);
            case BOOKS:
                return new BookXPathParser(fileContent);
            default:
                throw new RuntimeException("Unknown file name");
        }
    }

    private enum PageType {
        OVERSTOCK("^.*jewelry\\d+.html$"),
        RTV("^.*rtv\\d+.html$"),
        BOOKS("^.*books\\d+.html$"),
        NOT_FOUND("$a");

        private final String filePrefix;

        PageType(String filePrefix) {
            this.filePrefix = filePrefix;
        }

        public String getFilePattern() {
            return filePrefix;
        }

        public static PageType getType(final String fileName) {
            return Arrays.stream(PageType.values())
                         .filter(o -> fileName.matches(o.getFilePattern()))
                         .findAny()
                         .orElse(NOT_FOUND);
        }
    }
}
