package pl.kosieradzki.lessons;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * The LessonsFilter class is used to filter a collection of lessons based on a predefined list
 * of lesson names. It ensures that only lessons matching the filter list are included in
 * the result after filtering.
 */
public class LessonsFilter {
    private final List<String> filter = new ArrayList<>();

    /**
     * @param filter A list of lesson names that should _not_ be filtered out.
     *               The values in the filter are converted to lowercase and trimmed for consistent comparison.
     */
    public LessonsFilter(List<String> filter) {
        for (String s : filter) {
            this.filter.add(s.trim().toLowerCase());
        }
    }

    /**
     * Filters a collection of lesson elements based on a predefined filter list.
     * Only lessons that are present in the predefined filter list will be included
     * in the returned collection.
     *
     * @param allLessons the complete collection of lesson elements to be filtered
     * @return a collection of lessons that match the predefined filter list
     */
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

    /**
     * Checks if a lesson is present in the predefined filter list by comparing its name
     * (obtained using the provided LessonsConverter) with each filter entry.
     *
     * @param lessonsConverter the LessonsConverter instance used to retrieve the name of the lesson
     * @param lesson           the lesson element whose name is to be checked against the filter list
     * @return true if the lesson's name matches one of the entries in the filter list; false otherwise
     */
    private boolean isLessonOnFilterList(LessonsConverter lessonsConverter, Element lesson) {
        for (String lessonNameFilter : filter) {
            if (lessonsConverter.getLessonName(lesson).equals(lessonNameFilter)) {
                return true;
            }
        }
        return false;
    }
}
