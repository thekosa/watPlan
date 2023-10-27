package pl.kosieradzki.Calendar;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CalendarEventEntity {
    private String summary;
    private String location;
    private DateTime startDateTime;
    private DateTime endDateTime;

    public CalendarEventEntity(String summary, String location, DateTime startDateTime, DateTime endDateTime) {
        this.summary = summary;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
