package pl.kosieradzki.Calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.services.calendar.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

public class CalendarHandler {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "My Project 47501";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = this.getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline")
                        .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("kosecki.testowy");
    }

    private com.google.api.services.calendar.Calendar getService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<CalendarListEntry> getCalendars() throws GeneralSecurityException, IOException {
        CalendarList calendarList = getService().calendarList().list().execute();
        return calendarList.getItems();
    }

    public void deleteCalendar(String calendarId) throws GeneralSecurityException, IOException {
        getService().calendars().delete(calendarId).execute();
        System.out.println("Calendar deleted: " + calendarId);
    }

    public String insertCalendar(String calendarSummary) throws GeneralSecurityException, IOException {
        Calendar calendar = new Calendar();
        calendar.setSummary(calendarSummary).setTimeZone("Europe/Warsaw");

        Calendar createdCalendar = getService().calendars().insert(calendar).execute();
        System.out.println("Calendar inserted: " + createdCalendar.getSummary());
        return createdCalendar.getId();
    }

    public void insertEvents(List<CalendarEventEntity> items, String calendarId) throws GeneralSecurityException, IOException {
        for (CalendarEventEntity item : items) {
            insertEvent(item, calendarId);
        }
    }

    private void insertEvent(CalendarEventEntity item, String calendarId) throws GeneralSecurityException, IOException {
        Event event = new Event();
        EventDateTime start = new EventDateTime()
                .setDateTime(item.getStartDateTime())
                .setTimeZone(item.getTimeZone());
        EventDateTime end = new EventDateTime()
                .setDateTime(item.getEndDateTime())
                .setTimeZone(item.getTimeZone());

        event.setSummary(item.getSummary())
                .setLocation(item.getLocation())
                .setStart(start)
                .setEnd(end);

        getService().events().insert(calendarId, event).execute();
        System.out.println("Event inserted: " + event.getSummary());
    }
}
