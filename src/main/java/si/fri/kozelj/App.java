package si.fri.kozelj;

import si.fri.kozelj.parsers.Parser;
import si.fri.kozelj.parsers.ParserFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class App {
    private static final String PAGE_PATH = "/pages/";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough arguments. First should be method (regex, xpath, roadrunner), second file");
        }

        String method = args[0];
        String file = args[1];

        parseFile(method, file);
    }

    private static void parseFile(String method, String fileName) {
        String fileContent;
        try {
            fileContent = loadFromFile(method, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading from file: " + fileName);
        }

        ParserFactory parserFactory = new ParserFactory(method, fileName);
        Parser parser = parserFactory.getParser(fileContent);

        System.out.println(parser.parseJson());
    }

    private static String loadFromFile(String method, String fileName) throws IOException {
        InputStreamReader inputStreamReader;
        if (method.endsWith("-via-path")) {
            // load from file path
            inputStreamReader = new FileReader(new File(fileName));
        } else {
            // load from file in resources folder
            URL url = App.class.getResource(PAGE_PATH + fileName);
            if (url == null) {
                throw new RuntimeException("File resource does not exists for file: " + fileName);
            }

            inputStreamReader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }
}
