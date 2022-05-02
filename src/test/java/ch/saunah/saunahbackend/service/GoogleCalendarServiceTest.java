package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.model.Event;

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

    @Test
    public void testDemoListEvents() throws IOException {
        List<Event> events = calendarService.getNextEvents(TEST_CALENDAR_ID);

        assertNotNull(events);
        System.out.println(events);
    }
}
