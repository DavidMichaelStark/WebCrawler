import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> urls = promptForURLs(scanner);
        int depth = promptForDepth(scanner);
        String targetLanguage = promptForTargetLanguage(scanner);

        ExecutorService executor = Executors.newFixedThreadPool(urls.size());
        List<Future<ReportGenerator>> futureReports = new ArrayList<>();

        for (String url : urls) {
            WebCrawler crawler = new WebCrawler(url, depth, targetLanguage);
            Future<ReportGenerator> futureReport = executor.submit(() -> crawler.crawl());
            futureReports.add(futureReport);
        }

        ReportGenerator report = new ReportGenerator();
        for (Future<ReportGenerator> futureReport : futureReports) {
            try {
                report.merge(futureReport.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        report.saveToFile("example-report.md");
        executor.shutdown();

        System.out.println("Crawl completed. Check example-report.md for the results.");
    }

    private static List<String> promptForURLs(Scanner scanner) {
        System.out.print("Enter the URLs (separated by comma): ");
        return Arrays.asList(scanner.nextLine().split(",\\s*"));
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