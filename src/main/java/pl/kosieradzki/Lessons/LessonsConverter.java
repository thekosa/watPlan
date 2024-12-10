package pl.kosieradzki.lessons;

import com.google.api.client.util.DateTime;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.kosieradzki.calendar.CalendarEventEntity;
import pl.kosieradzki.lessons.block.Blocks;
import pl.kosieradzki.lessons.block.BlockNumb;

import java.time.*;
import java.util.*;

/**
 * Handles conversion of lesson data into various formats, including CalendarEventEntity lists
 * and CSV-formatted string lists. This class processes HTML elements representing lessons, extracts
 * specific details, and formats them appropriately based on the target output format.
 */
public class LessonsConverter {
    private final Blocks blocks = new Blocks();

    /**
     * Converts a collection of lesson HTML elements into a list of CalendarEventEntity objects.
     * Each input lesson element is parsed into detailed information including its summary, location,
     * start time, and end time, then encapsulated into a CalendarEventEntity.
     *
     * @param lessons the collection of HTML elements representing lessons to be converted
     * @return a list of CalendarEventEntity objects created from the provided lesson elements
     */
    public List<CalendarEventEntity> elements2CEE(Elements lessons) {
        List<CalendarEventEntity> cee = new ArrayList<>();
        for (Element lesson : lessons) {
            cee.add(new CalendarEventEntity(constructName(lesson),
                    constructLocalization(lesson),
                    constructStartDateTime(lesson),
                    constructEndDateTime(lesson)));
        }
        return cee;
    }

    /**
     * Converts a collection of HTML elements representing lessons into a list of CSV-formatted strings.
     * Each lesson element is parsed to extract detailed information including its name, location, start date,
     * start time, end date, end time, and reminder settings, formatted as a CSV row.
     *
     * @param lessons the collection of HTML elements representing lessons to be converted
     * @return a list of strings, where the first entry is the CSV header and subsequent entries represent
     * individual lessons in CSV format
     */
    public List<String> elements2csv(Elements lessons) {
        List<String> csvLessons = new ArrayList<>();
        csvLessons.add("Temat,Lokalizacja,Data rozpoczęcia,Czas rozpoczęcia,Data zakończenia,Czas zakończenia,Przypomnienie wł./wył.,Data przypomnienia,Czas przypomnienia");
        for (Element lesson : lessons) {
            csvLessons.add(constructName(lesson) + ","
                    + constructLocalization(lesson) + ","
                    + constructDate(lesson) + ","
                    + constructStartTime(lesson) + ","
                    + constructDate(lesson) + ","
                    + constructEndTime(lesson) + ","
                    + "Fałsz" + ","
                    + constructDate(lesson) + ","
                    + constructStartTime(lesson));
        }
        return csvLessons;
    }

    /**
     * Constructs the start date and time for an individual lesson based on the provided lesson element.
     * The date and time are built using the lesson's date, start time, and an appropriate timezone offset
     * depending on whether it falls under daylight saving time or not.
     *
     * @param lesson the HTML element representing the lesson whose start date and time is to be constructed
     * @return a DateTime object representing the start date and time of the lesson
     */
    private DateTime constructStartDateTime(Element lesson) {
        String date = constructDate(lesson);
        if (isSummerTime(date)) {
            return new DateTime(date + "T" + constructStartTime(lesson) + ":00+02:00");
        } else {
            return new DateTime(date + "T" + constructStartTime(lesson) + ":00+01:00");
        }
    }

    /**
     * Constructs the end date and time for the given lesson based on the provided lesson element.
     * The end date and time are derived by combining the lesson's date, end time, and an appropriate
     * timezone offset depending on whether the date falls under daylight saving time.
     *
     * @param lesson the HTML element representing the lesson whose end date and time is to be constructed
     * @return a DateTime object representing the end date and time of the lesson
     */
    private DateTime constructEndDateTime(Element lesson) {
        String date = constructDate(lesson);
        if (isSummerTime(date)) {
            return new DateTime(date + "T" + constructEndTime(lesson) + ":00+02:00");
        } else {
            return new DateTime(date + "T" + constructEndTime(lesson) + ":00+01:00");
        }
    }

    /**
     * Determines whether a given date falls within the summer time
     * period for the Europe/Warsaw timezone.
     *
     * @param date the date to check, provided as a string in the format "yyyy-MM-dd"
     * @return true if the specified date is in summer time (daylight saving time), false otherwise
     */
    private boolean isSummerTime(String date) {
        String[] yearMonthDay = date.split("-");
        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.parseInt(yearMonthDay[0]),
                Integer.parseInt(yearMonthDay[1]),
                Integer.parseInt(yearMonthDay[2]),
                12,
                0);
        return zoneId.getRules().isDaylightSavings(ZonedDateTime.of(localDateTime, zoneId).toInstant());
    }

    /**
     * Constructs the end time for a given lesson based on its block ID.
     * The method retrieves the block ID of the lesson, maps it to a corresponding
     * block number, and accesses the pre-defined end time associated with that block.
     *
     * @param lesson the HTML element representing the lesson whose end time is to be constructed
     * @return a string representing the end time of the lesson
     */
    private String constructEndTime(Element lesson) {
        int id = getBlockId(lesson);
        return blocks.getBlocks().get(BlockNumb.values()[id]).getEnd();
    }

    /**
     * Constructs the start time for a given lesson based on its block ID.
     * The method determines the block ID of the lesson, maps it to a corresponding
     * block number, and retrieves the predefined start time associated with that block.
     *
     * @param lesson the HTML element representing the lesson whose start time is to be constructed
     * @return a string representing the start time of the lesson
     */
    private String constructStartTime(Element lesson) {
        int id = getBlockId(lesson);
        return blocks.getBlocks().get(BlockNumb.values()[id]).getStart();
    }

    /**
     * Constructs a date string for the given lesson element by extracting its date field,
     * ensuring it is non-null, and replacing all underscores with hyphens.
     *
     * @param lesson the HTML element representing the lesson from which the date is to be extracted
     * @return a string representing the date of the lesson, with underscores replaced by hyphens
     */
    private String constructDate(Element lesson) {
        Element date = lesson.select(".date").first();
        assert date != null;
        return date.text().replaceAll("_", "-");
    }

    /**
     * Extracts and constructs a localized string from the provided lesson HTML element.
     * The method retrieves the first occurrence of the ".name" element within the given
     * lesson, ensuring its existence, then processes its contents to extract and return
     * a specific substring.
     *
     * @param lesson the HTML element representing the lesson, which contains the ".name"
     *               element used for extracting the localization string
     * @return a string representing the constructed localization, derived from the
     * ".name" element of the lesson
     */
    private String constructLocalization(Element lesson) {
        Element name = lesson.select(".name").first();
        assert name != null;
        return name.toString().split("<br>")[2].substring(1);
    }

    /**
     * Constructs the name of a lesson by extracting and combining specific elements
     * from the provided HTML element. It processes the ".name" and ".info" selectors
     * within the lesson element to construct a detailed name string.
     *
     * @param lesson the HTML element representing the lesson from which the name is to be constructed
     * @return a string combining the extracted name, type, and number information of the lesson
     */
    private String constructName(Element lesson) {
        Element name = lesson.select(".name").first();
        Element info = lesson.select(".info").first();

        assert info != null;
        String infoName = info.text().split("-")[0];

        assert name != null;
        String type = name.toString().split("<br>")[1].substring(1);
        String numb = "[" + name.toString().split("<br>")[3].split("\\[")[1].split("<")[0];

        return infoName + type + " " + numb;
    }

    /**
     * Extracts the block ID from the given HTML element representing a lesson.
     * The method selects the first occurrence of an element with a class named "block_id",
     * ensures that it is not null, extracts its text content, and processes this content
     * to calculate and return the block ID.
     *
     * @param lesson the HTML element representing the lesson, from which the block ID is extracted
     * @return the integer value of the block ID after parsing and adjustment
     * @throws NullPointerException  if the element with the class "block_id" is not found within the lesson element
     * @throws NumberFormatException if the extracted text cannot be parsed into an integer
     */
    private int getBlockId(Element lesson) {
        Element blockId = lesson.select(".block_id").first();
        assert blockId != null;
        return Integer.parseInt(blockId.text().substring(5)) - 1;
    }

    /**
     * Extracts and returns the name of a lesson from the provided lesson HTML element.
     * The method selects the first element with the class "info", ensures it is non-null,
     * retrieves its text content, splits it by a hyphen, and processes the result to
     * obtain the lesson name in lowercase and trimmed format.
     *
     * @param lesson the HTML element representing the lesson from which the name is to be extracted
     * @return a string representing the name of the lesson in lowercase and trimmed format
     */
    public String getLessonName(Element lesson) {
        Element info = lesson.select(".info").first();

        assert info != null;
        return info.text().split("-")[0].trim().toLowerCase();
    }
}
