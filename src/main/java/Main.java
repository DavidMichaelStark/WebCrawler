import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String url = promptForURL(scanner);
        int depth = promptForDepth(scanner);
        String targetLanguage = promptForTargetLanguage(scanner);

        WebCrawler crawler = new WebCrawler(url, depth, targetLanguage);

        ReportGenerator report = new ReportGenerator();
        report.addInputURL(url);
        report.addDepth(depth);
        report.addSourceLanguage("english");
        report.addTargetLanguage(targetLanguage);

        crawler.crawl(report);
        report.saveToFile("example-report.md");

        System.out.println("Crawl completed. Check example-report.md for the results.");
    }

    private static String promptForURL(Scanner scanner) {
        System.out.print("Enter the URL: ");
        return scanner.nextLine();
    }

    private static int promptForDepth(Scanner scanner) {
        System.out.print("Enter the depth of websites to crawl: ");
        return scanner.nextInt();
    }

    private static String promptForTargetLanguage(Scanner scanner) {
        System.out.print("Enter the target language (e.g., 'de' for German): ");
        return scanner.next();
    }
}
