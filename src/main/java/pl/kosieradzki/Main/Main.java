package pl.kosieradzki.Main;

import com.google.api.services.calendar.model.CalendarListEntry;
import org.jsoup.select.Elements;
import pl.kosieradzki.calendar.CalendarEventEntity;
import pl.kosieradzki.calendar.CalendarHandler;
import pl.kosieradzki.FileHandler;
import pl.kosieradzki.lessons.Lessons;
import pl.kosieradzki.lessons.LessonsConverter;
import pl.kosieradzki.lessons.LessonsFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        System.out.println("Hello world!");


        final String configFilePath = "src/main/resources/config/";
        final String filtersFilePath = "src/main/resources/config/filters/";

        FileHandler fileHandler = new FileHandler();

        List<String> groups = fileHandler.readFile(configFilePath + "groups");

        for (String group : groups) {
            Lessons lessons = new Lessons(group);
            Elements lessonsNotFiltered = lessons.getLessons();

            List<String> groupFilter = fileHandler.readFile(filtersFilePath + group);
            LessonsFilter lessonsFilter = new LessonsFilter(groupFilter);
            Elements lessonsFiltered = lessonsFilter.filterOut(lessonsNotFiltered);

            LessonsConverter lessonsConverter = new LessonsConverter();
            List<CalendarEventEntity> lessonsConverted = lessonsConverter.elements2CEE(lessonsFiltered);

            CalendarHandler calendarHandler = new CalendarHandler();
            List<CalendarListEntry> calendarsList = calendarHandler.getCalendars();

            for (CalendarListEntry calendar : calendarsList) {
                if (calendar.getSummary().equals(group)) {
                    calendarHandler.deleteCalendar(calendar.getId());
                    break;
                }
            }

            String calendarId = calendarHandler.insertCalendar(group);

            calendarHandler.insertEvents(lessonsConverted, calendarId);
        }
    }
}