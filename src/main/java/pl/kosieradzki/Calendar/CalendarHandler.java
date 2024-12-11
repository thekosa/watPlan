package pl.kosieradzki.calendar;

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

/**
 * Handles operations related to Google Calendar, including authentication, retrieving,
 * creating, and deleting calendars and events. This class leverages the Google Calendar API to interact
 * with user calendars and provides methods for managing calendar data programmatically.
 */
public class CalendarHandler {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "WatPlan";
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
     * Represents the user identifier associated with the Google API credential.
     */
    private final String userId;

    /**
     * Constructs a new CalendarHandler instance for managing Google Calendar operations
     * using the specified user credential.
     *
     * @param userId the authorized credential object for accessing Google Calendar services
     */
    public CalendarHandler(String userId) {
        this.userId = userId;
    }

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
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
    }

    /**
     * Creates and returns an authorized Google Calendar API client service.
     *
     * @return an authorized instance of {@link com.google.api.services.calendar.Calendar}
     * that can be used to interact with the Google Calendar API.
     * @throws IOException              if an input or output error occurs while setting up the service.
     * @throws GeneralSecurityException if a security-related issue occurs during service initialization.
     */
    private com.google.api.services.calendar.Calendar getService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Retrieves a list of calendars accessible by the authenticated user.
     *
     * @return a list of {@link CalendarListEntry} objects representing the accessible calendars.
     * @throws GeneralSecurityException if a security-related issue occurs during the operation.
     * @throws IOException              if an input or output error occurs while communicating with the calendar service.
     */
    public List<CalendarListEntry> getCalendars() throws GeneralSecurityException, IOException {
        CalendarList calendarList = getService().calendarList().list().execute();
        return calendarList.getItems();
    }

    /**
     * Deletes a calendar identified by its unique identifier.
     *
     * @param calendarId the unique identifier of the calendar to be deleted
     * @throws GeneralSecurityException if a security-related issue occurs during the operation
     * @throws IOException              if an input or output error occurs while communicating with the calendar service
     */
    public void deleteCalendar(String calendarId) throws GeneralSecurityException, IOException {
        getService().calendars().delete(calendarId).execute();
        System.out.println("Calendar deleted: " + calendarId);
    }

    /**
     * Inserts a new calendar with the specified summary and returns its unique identifier.
     *
     * @param calendarSummary the summary or name of the calendar to be created
     * @return the unique identifier of the newly created calendar
     * @throws GeneralSecurityException if a security-related issue occurs during the process
     * @throws IOException              if an input or output error occurs while communicating with the calendar service
     */
    public String insertCalendar(String calendarSummary) throws GeneralSecurityException, IOException {
        Calendar calendar = new Calendar();
        calendar.setSummary(calendarSummary).setTimeZone("Europe/Warsaw");

        Calendar createdCalendar = getService().calendars().insert(calendar).execute();
        System.out.println("Calendar inserted: " + createdCalendar.getSummary());
        return createdCalendar.getId();
    }

    /**
     * Inserts a list of calendar events into a specified calendar.
     *
     * @param items      the list of CalendarEventEntity objects representing the events to be inserted.
     *                   Each object contains details such as event summary, location, start time, and end time.
     * @param calendarId the unique identifier of the calendar where the events should be inserted.
     * @throws GeneralSecurityException if a security issue occurs during the process of accessing the calendar service.
     * @throws IOException              if an input or output issue occurs while communicating with the calendar service.
     */
    public void insertEvents(List<CalendarEventEntity> items, String calendarId) throws GeneralSecurityException, IOException {
        for (CalendarEventEntity item : items) {
            insertEvent(item, calendarId);
        }
    }

    /**
     * Inserts an event into a specified calendar.
     *
     * @param item       the event details encapsulated in a CalendarEventEntity object. This includes the event summary,
     *                   location, start time, and end time.
     * @param calendarId the unique identifier of the calendar where the event should be inserted.
     * @throws GeneralSecurityException if a security issue occurs during the process of accessing the service.
     * @throws IOException              if an error occurs while communicating with the calendar service.
     */
    private void insertEvent(CalendarEventEntity item, String calendarId) throws GeneralSecurityException, IOException {
        Event event = new Event();
        EventDateTime start = new EventDateTime().setDateTime(item.getStartDateTime());
        EventDateTime end = new EventDateTime().setDateTime(item.getEndDateTime());

        event.setSummary(item.getSummary())
                .setLocation(item.getLocation())
                .setStart(start)
                .setEnd(end);

        getService().events().insert(calendarId, event).execute();
        System.out.println("Event inserted: " + event.getSummary());
//      System.out.println("cały ivent: " + event);
    }
}
