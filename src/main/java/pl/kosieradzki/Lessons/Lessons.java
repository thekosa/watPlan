package pl.kosieradzki.lessons;

import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.kosieradzki.FileHandler;
import pl.kosieradzki.SSLHelper.SSLHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

@Getter
public class Lessons {
    private static final String LESSONS_CONTAINER_SELECTOR = ".lessons";
    private static final String LESSON_ITEM_SELECTOR = ".lesson";
    private Elements lessons;
    private final String url;

    public Lessons(String groupName) throws FileNotFoundException {
        FileHandler fileHandler = new FileHandler();
        url = fileHandler.readFileToString("src/main/resources/config/watURL") + groupName;
        fetchLessonsFromUrl();
    }

    private void fetchLessonsFromUrl() {
        Document fetchedDocument = getDocumentFromUrl(url);
        Element lessonsRootElement = fetchedDocument.select(LESSONS_CONTAINER_SELECTOR).first();

        if (lessonsRootElement == null) {
            throw new IllegalStateException("Unable to find lessons container: " + LESSONS_CONTAINER_SELECTOR);
        }

        lessons = lessonsRootElement.select(LESSON_ITEM_SELECTOR);
    }

    private Document getDocumentFromUrl(String url) {
        try {
            return SSLHelper.getConnection(url).get();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch document from URL: " + url, e);
        }
    }
}
