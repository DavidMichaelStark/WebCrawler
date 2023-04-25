import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DocumentExtensions {

    public static Elements selectHeadings(Document document) {
        return document.select("h1, h2, h3, h4, h5, h6");
    }

    public static Elements selectLinks(Document document) {
        return document.select("a[href]");
    }
}
