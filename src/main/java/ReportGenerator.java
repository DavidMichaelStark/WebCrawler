import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

/**
 * ReportGenerator is a class that generates a report with details about visited URLs, broken Links and
 * translated headings during the web crawling process.
 */
public class ReportGenerator {
    private final List<String> lines = new ArrayList<>();

    public void addInputURL(String url) {
        lines.add(String.format("input: <a>%s</a>", url));
    }

    public void addDepth(int depth) {
        lines.add("depth: " + depth);
    }

    public void addSourceLanguage(String sourceLanguage) {
        lines.add("source language: " + sourceLanguage);
    }

    public void addTargetLanguage(String targetLanguage) {
        lines.add("target language: " + targetLanguage);
    }

    public void addURLVisited(String url, String indentation) {
        lines.add(indentation + "--> link to <a>" + url + "</a>");
    }

    public void addTranslatedHeading(Element heading, String translatedText, String indentation) {
        String headingSymbols = "#".repeat(DocumentUtils.getHeadingLevel(heading));
        lines.add(indentation + headingSymbols + " --> " + translatedText);
    }

    public void addBrokenLink(String url, String indentation) {
        lines.add(indentation + "Error: Broken link <a>" + url + "</a>");
    }

    public void saveToFile(String filePath) {
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLines() {
        return lines;
    }

    public void merge(ReportGenerator other) {
        lines.addAll(other.getLines());
    }
}
