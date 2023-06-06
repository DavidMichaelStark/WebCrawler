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

    // Add the Google Cloud Translation API key here
    private static final String API_KEY = "Google_API_Translation_Key";

    private final String url;
    private final int depth;
    private final String targetLanguage;
    private final Set<String> visited = new HashSet<>();

    public WebCrawler(String url, int depth, String targetLanguage) {
        this.url = url;
        this.depth = depth;
        this.targetLanguage = targetLanguage;
    }

    public ReportGenerator crawl() {
        ReportGenerator report = new ReportGenerator();
        visitUrl(url, depth, report, "");
        return report;
    }

    private void visitUrl(String url, int depth, ReportGenerator report, String indentation) {
        if (shouldSkipCrawl(url, depth)) {
            return;
        }

        visited.add(url);
        report.addURLVisited(url, indentation);

        Document document = fetchDocument(url);
        if (document == null) {
            report.addBrokenLink(url, indentation);
            return;
        }

        processHeadings(document, report, indentation);
        if (depth > 0) {
            processLinks(document, depth, report, indentation);
        }
    }

    private boolean shouldSkipCrawl(String url, int depth) {
        return visited.contains(url) || depth < 0;
    }

    private Document fetchDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            return null;
        }
    }

    private void processHeadings(Document document, ReportGenerator report, String indentation) {
        Elements headings = DocumentUtils.selectHeadingElementsFromDocument(document);
        for (Element heading : headings) {
            String translatedText = translate(heading.text());
            report.addTranslatedHeading(heading, translatedText, indentation);
        }
    }

    private void processLinks(Document document, int depth, ReportGenerator report, String indentation) {
        Elements links = DocumentUtils.selectLinkElementsFromDocument(document);
        for (Element link : links) {
            visitUrl(link.attr("abs:href"), depth - 1, report, indentation + "  ");
        }
    }

    private String translate(String text) {
        Translate translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }
}
