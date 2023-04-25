import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    private ReportGenerator report;

    @BeforeEach
    void setUp() {
        report = new ReportGenerator();
    }

    @Test
    void addInputURLAddsLine() {
        report.addInputURL("https://example.com");

        assertEquals("input: <a>https://example.com</a>", report.getLines().get(0));
    }

    @Test
    void addDepthAddsLine() {
        report.addDepth(1);

        assertEquals("depth: 1", report.getLines().get(0));
    }

    @Test
    void addSourceLanguageAddsLine() {
        report.addSourceLanguage("en");

        assertEquals("source language: en", report.getLines().get(0));
    }

    @Test
    void addTargetLanguageAddsLine() {
        report.addTargetLanguage("de");

        assertEquals("target language: de", report.getLines().get(0));
    }

    @Test
    void addURLVisitedAddsLine() {
        report.addURLVisited("https://example.com", "");

        assertEquals("--> link to <a>https://example.com</a>", report.getLines().get(0));
    }

    @Test
    void addTranslatedHeadingAddsLine() {
        report.addTranslatedHeading(new Element("h1").text("Example Heading"), "Hello World!", "");

        assertEquals("# --> Hello World!", report.getLines().get(0));
    }

    @Test
    void addBrokenLinkAddsLine() {
        report.addBrokenLink("https://broken.example.com", "");

        assertEquals("Error: Broken link <a>https://broken.example.com</a>", report.getLines().get(0));
    }

    @Test
    void saveToFileSavesReportToFile(@TempDir Path tempDir) {
        report.addInputURL("https://example.com");
        report.addDepth(1);
        report.addSourceLanguage("en");
        report.addTargetLanguage("de");

        Path reportFile = tempDir.resolve("report.txt");
        report.saveToFile(reportFile.toString());

        assertTrue(reportFile.toFile().exists(), "Report file should be saved");
    }
}