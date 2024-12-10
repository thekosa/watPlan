package pl.kosieradzki.lessons;

import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.kosieradzki.FileHandler;
import pl.kosieradzki.SSLHelper.SSLHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Lessons class is responsible for fetching and storing lesson data from a specified URL
 * determined by a given group name. The lessons are retrieved as HTML elements and stored in
 * an instance of {@code Elements}.
 * <p>
 * The class uses a configuration file to determine the base URL, appending the group name
 * to construct the final URL to fetch data from. It ensures proper fetching of the lessons
 * container and individual lesson elements from the document, throwing exceptions if
 * the expected structure is not found or if fetching fails.
 */
@Getter
public class Lessons {
    private static final String LESSONS_CONTAINER_SELECTOR = ".lessons";
    private static final String LESSON_ITEM_SELECTOR = ".lesson";
    private Elements lessons;
    private final String url;

    /**
     * Constructs a Lessons object by fetching lesson data for the specified group name.
     * The constructor initializes the URL for the lessons by reading a base URL from a configuration file
     * and appending the given group name. It then attempts to fetch the lessons data from the constructed URL.
     *
     * @param groupName the name of the group to fetch lessons for. This value is appended to the base URL
     *                  read from the configuration file to form the complete URL.
     * @throws FileNotFoundException if the configuration file containing the base URL is not found.
     */
    public Lessons(String groupName) throws FileNotFoundException {
        FileHandler fileHandler = new FileHandler();
        url = fileHandler.readFileToString("src/main/resources/config/watURL") + groupName;
        fetchLessonsFromUrl();
    }

    /**
     * Fetches and processes lesson data from a specified URL. The method retrieves an HTML document
     * from the URL, extracts the container element holding the lessons, and selects all individual
     * lesson elements from within the container. The lessons are stored in the {@code lessons} field as
     * an {@code Elements} object.
     *
     * @throws IllegalStateException if the lessons container cannot be located in the fetched document
     *                               or if there is an issue constructing or fetching the document.
     */
    private void fetchLessonsFromUrl() {
        Document fetchedDocument = getDocumentFromUrl(url);
        Element lessonsRootElement = fetchedDocument.select(LESSONS_CONTAINER_SELECTOR).first();

        if (lessonsRootElement == null) {
            throw new IllegalStateException("Unable to find lessons container: " + LESSONS_CONTAINER_SELECTOR);
        }

        lessons = lessonsRootElement.select(LESSON_ITEM_SELECTOR);
    }

    /**
     * Fetches and returns an HTML document from the specified URL.
     * This method uses the {@code SSLHelper} utility to establish a secure connection to the URL
     * and then retrieves the document content.
     *
     * @param url the URL from which to fetch the HTML document
     * @return the fetched HTML document as a {@code Document} object
     * @throws IllegalStateException if an I/O error occurs while fetching the document
     */
    private Document getDocumentFromUrl(String url) {
        try {
            return SSLHelper.getConnection(url).get();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch document from URL: " + url, e);
        }
    }
}
