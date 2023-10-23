package pl.kosieradzki.Lessons;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LessonsFilter {
    private List<String> filter = new ArrayList<>();

    /**
     * @param filter : List of lessons that will not be deleted
     */
    public LessonsFilter(List<String> filter) {
        for (String s : filter) {
            this.filter.add(s.trim().toLowerCase());
        }
    }

    public Elements filterOut(Elements lessonsNotFiltered) {
        Elements lessonsFiltered = new Elements();
        for (Element lesson : lessonsNotFiltered) {
            boolean isOnList = false;
            for (String lessonNameFilter : filter) {
                if (new LessonsConverter().getLessonName(lesson).equals(lessonNameFilter)) {
                    isOnList = true;
                    break;
                }
            }
            if (isOnList) {
                lessonsFiltered.add(lesson);
            }
        }
        return lessonsFiltered;
    }
}
