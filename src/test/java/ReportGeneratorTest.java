import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {
    private ReportGenerator report;

    @BeforeEach
    void setUp() {
        report = new ReportGenerator();
    }

    @Test
    void testInputURL() {
        report.addInputURL("https://webscraper.io/test-sites/e-commerce/allinone");
        assertEquals("input: <a>https://webscraper.io/test-sites/e-commerce/allinone</a>", report.getLines().get(0));
    }

    @Test
    void testDepth() {
        report.addDepth(1);
        assertEquals("depth: 1", report.getLines().get(0));
    }

    @Test
    void testSourceLanguage() {
        report.addSourceLanguage("en");
        assertEquals("source language: en", report.getLines().get(0));
    }

    @Test
    void testTargetLanguage() {
        report.addTargetLanguage("de");
        assertEquals("target language: de", report.getLines().get(0));
    }

    @Test
    void testURLVisited() {
        report.addURLVisited("https://webscraper.io/test-sites/e-commerce/allinone", "");
        assertEquals("--> link to <a>https://webscraper.io/test-sites/e-commerce/allinone</a>", report.getLines().get(0));
    }

    @Test
    void testTranslatedHeading() {
        report.addTranslatedHeading(new Element("h1").text("Example Heading"), "Hello World!", "");
        assertEquals("# --> Hello World!", report.getLines().get(0));
    }

    @Test
    void testBrokenLink() {
        report.addBrokenLink("https://webscraper.io/downloads/Web_Scraper_Media_Kit.zip", "");
        assertEquals("Error: Broken link <a>https://webscraper.io/downloads/Web_Scraper_Media_Kit.zip</a>", report.getLines().get(0));
    }

    @Test
    void testSaveToFile(@TempDir Path tempDir) {
        report.addTranslatedHeading(new Element("h1").text("Example Heading"), "Hello World!", "");
        report.saveToFile(tempDir.resolve("report.txt").toString());
        assertTrue(Files.exists(tempDir.resolve("report.txt")));
    }

    @Test
    void testGetLines() {
        report.addTranslatedHeading(new Element("h1").text("Example Heading"), "Hello World!", "");
        assertEquals(1, report.getLines().size());
    }
}
