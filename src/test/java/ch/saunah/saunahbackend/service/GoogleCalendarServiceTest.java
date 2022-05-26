package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.repository.UserRepository;

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

    private static final String TEST_CALENDAR_ID = "cs85d7fer742u5v5r4v6e7jink@group.calendar.google.com";


    /**
     * Test if the authenicate is functioning
     *
     * @throws IOException
     */
    @Test
    void testDemoListEvents() throws IOException {
        List<Event> events = calendarService.getNextEvents(TEST_CALENDAR_ID);

        assertNotNull(events);
    }

    /**
     * Testing all CRUD operation on the google api
     *
     * @throws IOException
     */
    @Test
    void crudCalenderTest() throws IOException {
        Event event = createEvent();
        String eventID = calendarService.insertEvent(TEST_CALENDAR_ID, event);
        assertNotNull(eventID);
        assertDoesNotThrow(() -> calendarService.getEvent(TEST_CALENDAR_ID, eventID));
        assertDoesNotThrow(() -> calendarService.updateEvent(TEST_CALENDAR_ID, eventID, new DateTime(System.currentTimeMillis() + 20000), new DateTime(System.currentTimeMillis() + 30000)));
        assertDoesNotThrow(() -> calendarService.deleteEvent(TEST_CALENDAR_ID, eventID));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void bookingCalenderTest() throws Exception {
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
        sauna.setGoogleCalendarId(TEST_CALENDAR_ID);
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
        user.setZip("1324");
        user.setActivationId("activated");
        user.setActivated(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);
        BookingBody bookingBody = new BookingBody();
        bookingBody.setStartBookingDate(new Date(System.currentTimeMillis() + 40000));
        bookingBody.setEndBookingDate(new Date(System.currentTimeMillis() + 50000));
        bookingBody.setSaunaId(sauna.getId());
        bookingBody.setLocation("Zürich");
        bookingBody.setTransportServiceDistance(20);
        bookingBody.setWashService(true);
        bookingBody.setSaunahImpAmount(2);
        bookingBody.setHandTowelAmount(2);
        bookingBody.setWoodAmount(2);
        bookingBody.setComment("very nice");

        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        String eventID = "";

        try {
            eventID = calendarService.createEvent(TEST_CALENDAR_ID, booking);
        } catch (IOException exception) {
            fail("Test failed");
        }

        String finalEventID = eventID;
        assertDoesNotThrow(() -> calendarService.approveEvent(TEST_CALENDAR_ID, finalEventID));
    }

    /**
     * Creates an event with values
     *
     * @return An Event
     */
    private Event createEvent() {
        Event event = new Event()
            .setSummary("Saunah Test")
            .setLocation("Saunah ")
            .setDescription("A chance to hear more about Google's developer products.");
        DateTime startDateTime = new DateTime(System.currentTimeMillis() + 10000);
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
