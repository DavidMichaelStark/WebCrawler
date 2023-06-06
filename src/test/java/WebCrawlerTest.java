import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebCrawlerTest {

    private List<String> urls;
    private int depth;
    private String targetLanguage;

    @BeforeEach
    void setUp() {
        urls = new ArrayList<>(Arrays.asList("https://webscraper.io/test-sites/e-commerce/allinone", "https://webscraper.io/test-sites/e-commerce/allinone/computers"));
        depth = 1;
        targetLanguage = "de";
    }

    @Test
    void shouldProcessHeadingsAndLinks() throws InterruptedException {
        crawlAndValidate((reports) -> {
            for (ReportGenerator report : reports) {
                assertTrue(report.getLines().size() > 0);
            }
        });
    }

    @Test
    void shouldHandleBrokenLinks() throws InterruptedException {
        String brokenUrl = "https://webscraper.io/downloads/Web_Scraper_Media_Kit.zip";
        urls.add(brokenUrl);

        crawlAndValidate((reports) -> {
            boolean containsError = reports.stream()
                    .flatMap(report -> report.getLines().stream())
                    .anyMatch(line -> line.contains("Error: Broken link") && line.contains(brokenUrl));
            assertTrue(containsError);
        });
    }

    @Test
    void shouldSkipCrawlingWhenDepthIsNegative() throws InterruptedException {
        depth = -1;

        crawlAndValidate((reports) -> {
            for (ReportGenerator report : reports) {
                assertEquals(0, report.getLines().size());
            }
        });
    }

    private interface ReportValidator {
        void validate(List<ReportGenerator> reports);
    }

    private void crawlAndValidate(ReportValidator validator) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        List<ReportGenerator> reports = Collections.synchronizedList(new ArrayList<>());

        for (String url : urls) {
            WebCrawler webCrawler = new WebCrawler(url, depth, targetLanguage);
            Thread thread = new Thread(() -> {
                ReportGenerator report = webCrawler.crawl();
                reports.add(report);
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        validator.validate(reports);
    }
}
