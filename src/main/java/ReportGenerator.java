import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

/**
 * ReportGenerator is a class that generates a report with details about visited URLs, broken Links and
 * translated headings during the web crawling process.
 */
public class ReportGenerator {

    private List<String> lines;

    public ReportGenerator() {
        lines = new ArrayList<>();
    }

    public void addInputURL(String url) {
        addLine("input: <a>" + url + "</a>");
    }

    public void addDepth(int depth) {
        addLine("depth: " + depth);
    }

    public void addSourceLanguage(String sourceLanguage) {
        addLine("source language: " + sourceLanguage);
    }

    public void addTargetLanguage(String targetLanguage) {
        addLine("target language: " + targetLanguage);
    }

    public void addURLVisited(String url, String indentation) {
        addLine(indentation + "--> link to <a>" + url + "</a>");
    }

    public void addTranslatedHeading(Element heading, String translatedText, String indentation) {
        String headingSymbols = "#".repeat(Integer.parseInt(heading.tagName().substring(1)));
        addLine(indentation + headingSymbols + " --> " + translatedText);
    }

    public void addBrokenLink(String url, String indentation) {
        addLine(indentation + "Error: Broken link <a>" + url + "</a>");
    }

    public void saveToFile(String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(new File(filePath));
            for (String line : lines) {
                fileWriter.write(line + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLine(String line) {
        lines.add(line);
    }

    public List<String> getLines() {
        return lines;
    }
}
