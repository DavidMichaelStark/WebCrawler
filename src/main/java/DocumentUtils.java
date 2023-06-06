import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocumentUtils {

    public static Elements selectHeadingElementsFromDocument(Document document) {
        return document.select("h1, h2, h3, h4, h5, h6");
    }

    public static Elements selectLinkElementsFromDocument(Document document) {
        return document.select("a[href]");
    }

    public static int getHeadingLevel(Element heading) {
        return Integer.parseInt(heading.tagName().substring(1));
    }
}
