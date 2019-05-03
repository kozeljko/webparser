package si.fri.kozelj;

import si.fri.kozelj.regex.AbstractRegexParser;
import si.fri.kozelj.regex.OverstockRegexParser;
import si.fri.kozelj.regex.RtvRegexParser;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    private static final String PAGE_PATH = "/pages/";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough arguments. First should be method (regex, xpath, roadrunner), second file");
        }

        String method = args[0];
        String file = args[1];

        switch (method) {
            case "regex":
                handleRegex(file);
                break;
            case "xpath":
            default:
                throw new RuntimeException("Unknown method");
        }
    }

    private static void handleRegex(String file) {
        String fileContent;
        try {
            fileContent = loadFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        AbstractRegexParser regexParser;
        switch (PageType.getType(file)) {
            case OVERSTOCK:
                regexParser = new OverstockRegexParser(fileContent);
                break;
            case RTV:
                regexParser = new RtvRegexParser(fileContent);
                break;
            default:
                throw new RuntimeException("Unknown file name");
        }

        System.out.println(regexParser.parseJson());
    }

    private static String loadFromFile(String fileName) throws IOException {
        URL url = App.class.getResource(PAGE_PATH + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }

    private enum PageType {
        OVERSTOCK("jewelry"),
        RTV("rtv"),
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
