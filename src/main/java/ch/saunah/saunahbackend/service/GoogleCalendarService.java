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
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);

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
     * @param httpTransport The network HTTP Transport.
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

    public String createCalender (String saunaName) throws IOException {
        com.google.api.services.calendar.model.Calendar calendar  = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(saunaName);
        calendar.setTimeZone("Switzerland/Bern");
        com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();
        return  createdCalendar.getId();
    }

    public List<Event> getNextEvents (String calenderID) throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list(calenderID)
            .setMaxResults(10)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        return events.getItems();
    }

    public String insertEvent (String calenderID , Event event) throws IOException {
        event = service.events().insert(calenderID, event).execute();
        return event.getId();
    }

    public void deleteEvent (String calenderID , String eventID ) throws IOException {
        service.events().delete(calenderID, eventID).execute();
    }

    public String updateEvent  (String calenderID ,String eventID ) throws IOException {
        service.events().get(calenderID, eventID).execute();
        return "";
    }
}
