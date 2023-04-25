import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * WebCrawler is a class that crawls web pages up to a specified depth and generates a report
 * of the visited URLs, broken Links and translated headings.
 */
public class WebCrawler {

    private String url;
    private int depth;
    private String targetLanguage;
    private Set<String> visited;

    // Add the Google Cloud Translation API key here
    private static final String API_KEY = "Google_API_Translation_Key";

    public WebCrawler(String url, int depth, String targetLanguage) {
        this.url = url;
        this.depth = depth;
        this.targetLanguage = targetLanguage;
        this.visited = new HashSet<>();
    }

    public void crawl(ReportGenerator report) {
        crawlRecursive(url, depth, report, "");
    }

    private void crawlRecursive(String url, int depth, ReportGenerator report, String indentation) {
        if (shouldSkipCrawl(url, depth)) {
            return;
        }

        visited.add(url);
        report.addURLVisited(url, indentation);

        try {
            Document document = Jsoup.connect(url).get();
            Elements headings = DocumentExtensions.selectHeadings(document);
            Elements links = DocumentExtensions.selectLinks(document);

            processHeadings(headings, report, indentation);
            processLinks(links, depth, report, indentation);

        } catch (IOException e) {
            report.addBrokenLink(url, indentation);
        }
    }

    private boolean shouldSkipCrawl(String url, int depth) {
        return depth < 0 || visited.contains(url);
    }

    private void processHeadings(Elements headings, ReportGenerator report, String indentation) {
        for (Element heading : headings) {
            String translatedText = translateText(heading.text(), targetLanguage);
            report.addTranslatedHeading(heading, translatedText, indentation);
        }
    }

    private void processLinks(Elements links, int depth, ReportGenerator report, String indentation) {
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            crawlRecursive(nextUrl, depth - 1, report, indentation + "  ");
        }
    }

    private String translateText(String text, String targetLanguage) {
        try {
            Translate translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();
            Translation translation = translate.translate(text,Translate.TranslateOption.targetLanguage(targetLanguage));
            return translation.getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
    }
}
