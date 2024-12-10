package pl.kosieradzki.lessons;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LessonsFilter {
    private final List<String> filter = new ArrayList<>();

    /**
     * @param filter : List of lessons that will not be deleted
     */
    public LessonsFilter(List<String> filter) {
        for (String s : filter) {
            this.filter.add(s.trim().toLowerCase());
        }
    }

    public Elements filterOut(Elements allLessons) {
        final LessonsConverter lessonsConverter = new LessonsConverter();
        Elements filteredLessons = new Elements();

        for (Element lesson : allLessons) {
            if (isLessonOnFilterList(lessonsConverter, lesson)) {
                filteredLessons.add(lesson);
            }
        }
        return filteredLessons;
    }

    private boolean isLessonOnFilterList(LessonsConverter lessonsConverter, Element lesson) {
        for (String lessonNameFilter : filter) {
            if (lessonsConverter.getLessonName(lesson).equals(lessonNameFilter)) {
                return true;
            }
        }
        return false;
    }
}
