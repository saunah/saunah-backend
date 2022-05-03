package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.SaunahBackendApplication;

/**
 * Google Calendar Service Integration Tests
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class GoogleCalendarServiceTest {
    @Autowired
    private GoogleCalendarService calendarService;

    private static final String TEST_CALENDAR_ID = "cs85d7fer742u5v5r4v6e7jink@group.calendar.google.com";

    /**
     * Test if the authenicate is functioning
     * @throws IOException
     */
    @Test
    void testDemoListEvents() throws IOException {
        List<Event> events = calendarService.getNextEvents(TEST_CALENDAR_ID);

        assertNotNull(events);
        System.out.println(events);
    }

    /**
     * Testing all CRUD operation on the google api
     * @throws IOException
     */
    @Test
    void CrudcalenderTest () throws IOException {
        Event event = createEvent();
        String eventID = calendarService.insertEvent(TEST_CALENDAR_ID,event);
        assertNotNull(eventID);
        assertDoesNotThrow(() -> calendarService.getEvent(TEST_CALENDAR_ID, eventID));
        assertDoesNotThrow(() -> calendarService.updateEvent(TEST_CALENDAR_ID, eventID , new DateTime(System.currentTimeMillis() + 20000),new DateTime( System.currentTimeMillis() + 30000)));
        assertDoesNotThrow(() -> calendarService.deleteEvent(TEST_CALENDAR_ID, eventID));
    }

    /**
     * Creates an event with values
     * @return An Event
     */
    private Event createEvent(){
            Event event = new Event()
                .setSummary("Saunah Test")
                .setLocation("Saunah ")
                .setDescription("A chance to hear more about Google's developer products.");
            DateTime startDateTime = new DateTime(System.currentTimeMillis() + 10000 );
            EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Zurich");
            event.setStart(start);

            DateTime endDateTime = new DateTime(System.currentTimeMillis() + 20000);
            EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Zurich");
            event.setEnd(end);
        return event;
    }
}
