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
    private Elements lessons;
    private final String url;

    public Lessons(String groupName) throws FileNotFoundException {
        FileHandler fileHandler = new FileHandler();
        url = fileHandler.readFileToString("src/main/resources/config/watURL") + groupName;
        loadLessons();
    }

    private void loadLessons() {
        Document document;
        try {
            document = SSLHelper.getConnection(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element lessonsContainer = document.select(".lessons").first();
        if (lessonsContainer != null) {
            lessons = lessonsContainer.select(".lesson");
        } else {
            throw new Error("Class lessons does not exist.");
        }
    }
}
