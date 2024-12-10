package pl.kosieradzki.calendar;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an event in a calendar with specific details such as summary, location,
 * and start and end times.
 * <p>
 * This class is primarily used to define an event that needs to be added to a calendar,
 * providing essential information required for event creation and management.
 * <p>
 * Fields:<br>
 * - summary: A brief description or title of the calendar event.<br>
 * - location: The location where the event will take place.<br>
 * - startDateTime: The starting date and time for the event.<br>
 * - endDateTime: The ending date and time for the event.<br>
 * <p>
 * Constructor: CalendarEventEntity(String summary, String location, DateTime startDateTime, DateTime endDateTime):
 * Initializes a CalendarEventEntity object with the given summary, location, start date-time, and end date-time.
 * <p>
 * Use cases include passing objects of this class to methods that interact with calendars,
 * such as inserting events into a Google Calendar.
 */
@Setter
@Getter
public class CalendarEventEntity {
    private String summary;
    private String location;
    private DateTime startDateTime;
    private DateTime endDateTime;

    /**
     * Constructs a new CalendarEventEntity object with the specified summary, location,
     * start date-time, and end date-time.
     *
     * @param summary       the summary or title of the calendar event
     * @param location      the location where the event is to take place
     * @param startDateTime the starting date and time of the event
     * @param endDateTime   the ending date and time of the event
     */
    public CalendarEventEntity(String summary, String location, DateTime startDateTime, DateTime endDateTime) {
        this.summary = summary;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
