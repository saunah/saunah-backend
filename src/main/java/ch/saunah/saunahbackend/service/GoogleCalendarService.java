package ch.saunah.saunahbackend.service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provides access to Google Services, such as the Google Calendar API.
 */
@Service
public class GoogleCalendarService {
    private static final String APPLICATION_NAME = "SAUNAH";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String TIMEZONE = "Europe/Zurich";

    private final NetHttpTransport httpTransport;

    private Calendar service;


    /**
     * Create a new Google Service instance
     * @param serviceAccountJsonB64 Base64 encoded JSON String of Google Service Account json
     * @throws GeneralSecurityException In case error creating new trusted transport.
     * @throws IOException In case of error when getting credentials.
     */
    public GoogleCalendarService(@Value("${saunah.credentials.google-service}") String serviceAccountJsonB64) throws GeneralSecurityException, IOException {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        ServiceAccountCredentials credentials = getCredentials(serviceAccountJsonB64);
        GoogleCredentials googleCredentials = credentials.createScoped(SCOPES);

        service = new Calendar.Builder(
            httpTransport,
            JSON_FACTORY,
            new HttpCredentialsAdapter(googleCredentials)
        )
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Creates ServiceAccountCredentials object
     * @param credentialsJsonB64 Base64 encoded credentials.json string
     * @return The ServiceAccountCredentials object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static ServiceAccountCredentials getCredentials(String credentialsJsonB64) throws IOException {
        byte[] credentialsJsonBytes = Base64.getDecoder().decode(credentialsJsonB64);
        String credentialsJson = new String(credentialsJsonBytes);
        InputStream credentialsStream = new ByteArrayInputStream(credentialsJson.getBytes());
        return ServiceAccountCredentials.fromStream(credentialsStream);
    }

    /**
     * Get the Next 10 events on the google calender
     * @param calenderID Id of the google Calender
     * @return The next ten events
     * @throws IOException
     */
    public List<Event> getNextEvents (String calenderID) throws IOException {
        return getEvents(calenderID , new DateTime(System.currentTimeMillis()) , 10 );
    }

    /**
     *  Get Events in a specific time period
     * @param calenderID Id of the google Calender
     * @param startTime Specify the startrange
     * @param maxResults Specify how many result are returned
     * @return List of events after the specific startTime
     * @throws IOException
     */
    public List<Event> getEvents (String calenderID,DateTime startTime, int maxResults) throws IOException {
        Events events = service.events().list(calenderID)
            .setMaxResults(maxResults)
            .setTimeMin(startTime)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        return events.getItems();
    }

    /**
     * Get an event with the event id
     * @param calenderID Id of the google Calender
     * @param eventID Id of the Event
     * @return Get the Event with the specified ID back
     * @throws IOException
     */
    public Event getEvent (String calenderID , String eventID) throws IOException {
        return service.events().get(calenderID, eventID).execute();
    }


    /**
     * Insert an Event on the Google Api
     * @param calenderID Id of the google Calender
     * @param event The event to insert in the google api
     * @return The id of the event
     * @throws IOException
     */
    public String insertEvent (String calenderID , Event event) throws IOException {
        event = service.events().insert(calenderID, event).execute();
        return event.getId();
    }
    /**
     * Delete an Event on the Google Api
     * @param calenderID Id of the google Calendar
     * @param eventID Id of the Event
     * @throws IOException
     */
    public void deleteEvent (String calenderID , String eventID ) throws IOException {
        service.events().delete(calenderID, eventID).execute();
    }

    /**
     * Change start and endtime on the specified event
     * @param calenderID Id of the google Calendar
     * @param eventID id of the event
     * @param startDate Starttime to what it will be changed
     * @param endDate Endtime to what it will be changed
     * @throws IOException
     */
    public void updateEvent  (String calenderID ,String eventID , DateTime startDate , DateTime endDate) throws IOException {
        Event event =service.events().get(calenderID, eventID).execute();
        EventDateTime start = new EventDateTime()
            .setDateTime(startDate)
            .setTimeZone(TIMEZONE);
        event.setStart(start);

        EventDateTime end = new EventDateTime()
            .setDateTime(endDate)
            .setTimeZone(TIMEZONE);
        event.setEnd(end);

        service.events().update(calenderID, event.getId(), event).execute();
    }
}
