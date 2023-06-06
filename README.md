# Web Crawler Project
This project is a simple web crawler that extracts headings from websites and translates them into a specified target language. It generates a report in Markdown format that contains the extracted information.

## Requirements
- Java 8
- Maven
- Google Cloud Translation API key

## Setup
1. Clone the repository or download the source code.
2. Import the project into your preferred IDE as a Maven project.
3. Add your Google Cloud Translation API key to the WebCrawler class in the API_KEY constant.
   - The specified Key can be found in the pdf-submission file.

## Running the Application
1. Open the Main class in your IDE.
2. Run the main method.
3. Input the URLs that are split by a comma, depth, and target language when prompted.
4. After the crawl is completed, you will find the generated report in the example-report.md file.

## Running Tests
The project includes JUnit tests to ensure the functionality of the web crawler.

Open the test classes in your IDE:

- WebCrawlerTest.java
- ReportGeneratorTest.java

Run the tests in each class using the built-in JUnit test runner of your IDE.

Verify that all tests pass.
