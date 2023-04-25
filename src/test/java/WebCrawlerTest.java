import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WebCrawlerTest {

    private WebCrawler webCrawler;
    private ReportGenerator report;

    @BeforeEach
    void setUp() {
        webCrawler = new WebCrawler("https://example.com", 1, "de");
        report = new ReportGenerator();
    }

    @Test
    void crawlProcessesHeadingsAndLinks() {
        webCrawler.crawl(report);

        assertTrue(report.getLines().size() > 0, "Report should contain some lines");
    }

    @Test
    void crawlSkipsCrawlingWhenDepthIsNegative() {
        WebCrawler webCrawlerWithNegativeDepth = new WebCrawler("https://example.com", -1, "de");
        webCrawlerWithNegativeDepth.crawl(report);

        assertEquals(0, report.getLines().size(), "Report should be empty when depth is negative");
    }
}
