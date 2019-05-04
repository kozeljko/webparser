package si.fri.kozelj.parsers;

public interface Parser {
    String parseJson();

    default String cleanMatch(String match) {
        return match.replaceAll("\\n", " ").replaceAll("[\\t]", "").trim();
    }
}
