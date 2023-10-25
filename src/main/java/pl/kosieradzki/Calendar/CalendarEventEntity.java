package pl.kosieradzki.Calendar;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Setter
@Getter
public class CalendarEventEntity {
    private String summary;
    private String location;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private String timeZone = "Europe/Warsaw";

    public CalendarEventEntity(String summary, String location, DateTime startDateTime, DateTime endDateTime) {
        this.summary = summary;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}