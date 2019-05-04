package si.fri.kozelj.parsers;

import si.fri.kozelj.parsers.regex.BookRegexParser;
import si.fri.kozelj.parsers.regex.OverstockRegexParser;
import si.fri.kozelj.parsers.regex.RtvRegexParser;
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
                return getRegexParser(fileContent);
            case "xpath":
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
//            case BOOKS:
//                return new BookRegexParser(fileContent);
            default:
                throw new RuntimeException("Unknown file name");
        }
    }

    private enum PageType {
        OVERSTOCK("jewelry"),
        RTV("rtv"),
        BOOKS("books"),
        NOT_FOUND("");

        private final String filePrefix;

        PageType(String filePrefix) {
            this.filePrefix = filePrefix;
        }

        public String getFilePrefix() {
            return filePrefix;
        }

        public static PageType getType(final String fileName) {
            return Arrays.stream(PageType.values()).filter(o -> fileName.startsWith(o.getFilePrefix())).findAny().orElse(NOT_FOUND);
        }
    }
}
