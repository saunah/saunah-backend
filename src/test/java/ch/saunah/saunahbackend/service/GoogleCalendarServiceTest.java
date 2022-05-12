package ch.saunah.saunahbackend.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.*;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.repository.UserRepository;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.saunah.saunahbackend.SaunahBackendApplication;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Google Calendar Service Integration Tests
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class GoogleCalendarServiceTest {

    @Autowired
    private SaunaRepository saunaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private GoogleCalendarService calendarService;

    private BookingBody bookingBody;

    private static final String TEST_CALENDAR_ID = "cs85d7fer742u5v5r4v6e7jink@group.calendar.google.com";



    /**
     * Test if the authenicate is functioning
     * @throws IOException
     */
    @Test
    void testDemoListEvents() throws IOException {
        List<Event> events = calendarService.getNextEvents(TEST_CALENDAR_ID);

        assertNotNull(events);
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

    @Test
    void bookingCalenderTest () throws Exception {
        Sauna sauna = new Sauna();
        sauna.setName("Mobile Sauna 1");
        sauna.setDescription("Eine Mobile Sauna");
        sauna.setIsMobile(true);
        sauna.setPrice(500);
        sauna.setMaxTemp(51);
        sauna.setNumberOfPeople(12);
        sauna.setLocation("Zürich");
        sauna.setStreet("Bahnhofstrasse 5");
        sauna.setZip(9000);
        sauna.setType("Tent");
        sauna.setGoogleCalenderID(TEST_CALENDAR_ID);
        saunaRepository.save(sauna);

        Price price = new Price();
        price.setTransportService(1.50F);
        price.setWashService(50.00F);
        price.setSaunahImp(25.00F);
        price.setDeposit(100F);
        price.setHandTowel(5.00F);
        price.setWood(20.00F);
        priceRepository.save(price);

        User user = new User();
        user.setFirstName("Hans");
        user.setLastName("Muster");
        user.setPlace("Winterthur");
        user.setEmail("hans.muster@mustermail.ch");
        user.setPhoneNumber("0123");
        user.setStreet("Teststrasse 123");
        user.setPasswordHash("ZH_a?!WD32");
        user.setPlz("1324");
        user.setActivationId("activated");
        user.setActivated(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);

        bookingBody = new BookingBody();
        bookingBody.setStartBookingDate(new Date(System.currentTimeMillis() + 40000));
        bookingBody.setEndBookingDate(new Date(System.currentTimeMillis() + 50000));
        bookingBody.setUserId(user.getId());
        bookingBody.setSaunaId(sauna.getId());
        bookingBody.setLocation("Zürich");
        bookingBody.setTransportService(true);
        bookingBody.setWashService(true);
        bookingBody.setSaunahImp(false);
        bookingBody.setDeposit(true);
        bookingBody.setHandTowel(false);
        bookingBody.setWood(true);

        Booking booking = bookingService.addBooking(bookingBody);
        String eventID = "";

        try {
            eventID = calendarService.createEvent(TEST_CALENDAR_ID,booking);
        }
        catch(IOException exception) {
            fail("Test failed");
        }

        String finalEventID = eventID;
        assertDoesNotThrow(() ->calendarService.approveEvent(TEST_CALENDAR_ID, finalEventID));
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
