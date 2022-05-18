package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.BookingRepository;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.repository.UserRepository;

/**
 * This class tests the booking service methods
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class BookingServiceTest {

    @Autowired
    private SaunaRepository saunaRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;
    private Sauna sauna = null;
    private User user = null;
    private BookingBody bookingBody = null;
    private static final String TEST_CALENDAR_ID = "cs85d7fer742u5v5r4v6e7jink@group.calendar.google.com";

    @BeforeEach
    void setUp() {
        sauna = new Sauna();
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
        price.setHourlyRate(20.00F);
        priceRepository.save(price);

        user = new User();
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

        bookingBody = new BookingBody();
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 20).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 1).getTime());
        bookingBody.setSaunaId(sauna.getId());
        bookingBody.setLocation("Zürich");
        bookingBody.setTransportServiceDistance(20);
        bookingBody.setWashService(true);
        bookingBody.setSaunahImpAmount(3);
        bookingBody.setHandTowelAmount(2);
        bookingBody.setWoodAmount(3);
        bookingBody.setComment("very nice");
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * This test checks if the user can create a new booking structure
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewBooking() throws Exception {
        int userId = user.getId();
        Booking booking = bookingService.addBooking(bookingBody, userId);
        Iterable<Booking> bookings = bookingRepository.findAll();
        assertTrue(bookings.iterator().hasNext());
        assertThrows(NullPointerException.class, () -> bookingService.addBooking(null, userId));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.MAY, 3).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 10).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 10).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 30).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 25).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 10).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 20).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 25).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 15).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 1).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.AUGUST, 20).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 1).getTime());
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody, userId));
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 10).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime());
        sauna.setGoogleCalendarId(null);
        Booking booking2 = bookingService.addBooking(bookingBody, userId);
        assertNotEquals(booking.getId(), booking2.getId());
    }

    /**
     * This test checks if a booking can be approved
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void approveBooking() throws Exception {
        assertThrows(NotFoundException.class, () -> bookingService.approveBooking(1));
        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        bookingService.approveBooking(booking.getId());
        assertEquals(BookingState.APPROVED, bookingService.getBooking(booking.getId()).getState());
    }

    /**
     * This test checks if a booking can be approved if the sauna does not have
     * a google calendar id.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void approveBookingWithoutGoogleCalenar() throws Exception {
        sauna.setGoogleCalendarId(null);
        saunaRepository.save(sauna);
        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        bookingService.approveBooking(booking.getId());
        assertEquals(BookingState.APPROVED, bookingService.getBooking(booking.getId()).getState());
    }

    /**
     * This test checks if a booking can be approved if event has empty google event id.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void approveBookingWithEmptyId() throws Exception {
        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        booking.setGoogleEventID("");
        bookingRepository.save(booking);
        bookingService.approveBooking(booking.getId());
        assertEquals(BookingState.APPROVED, bookingService.getBooking(booking.getId()).getState());
    }

    /**
     * This test checks if a booking can be canceled
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void cancelBooking() throws Exception {
        assertThrows(NotFoundException.class, () -> bookingService.cancelBooking(1));
        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        bookingService.cancelBooking(booking.getId());
        assertEquals(BookingState.CANCELED, bookingService.getBooking(booking.getId()).getState());
    }

    /**
     * This test checks if a booking can be canceled, if the sauna does not have
     * a google calendar id.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void cancelBookingWithoutGoogleCalendar() throws Exception {
        sauna.setGoogleCalendarId(null);
        saunaRepository.save(sauna);
        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        bookingService.cancelBooking(booking.getId());
        assertEquals(BookingState.CANCELED, bookingService.getBooking(booking.getId()).getState());
    }

    /**
     * This test checks if a booking can be canceled, if the booking does not have
     * a google calendar event id.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void cancelBookingWithEmptyId() throws Exception {
        Booking booking = bookingService.addBooking(bookingBody, user.getId());
        booking.setGoogleEventID(null);
        bookingRepository.save(booking);
        bookingService.cancelBooking(booking.getId());
        assertEquals(BookingState.CANCELED, bookingService.getBooking(booking.getId()).getState());
    }

    /**
     * This test checks if a booking can be found via it's id
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getBooking() throws Exception {
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(1));
        bookingService.addBooking(bookingBody, user.getId());
        assertNotNull(bookingService.getBooking(1));
    }

    /**
     * This test checks if all bookings can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllBooking() throws Exception {
        bookingService.addBooking(bookingBody, user.getId());
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.SEPTEMBER, 25).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.OCTOBER, 1).getTime());
        bookingService.addBooking(bookingBody, user.getId());
        bookingBody.setStartBookingDate(new GregorianCalendar(2022, Calendar.OCTOBER, 25).getTime());
        bookingBody.setEndBookingDate(new GregorianCalendar(2022, Calendar.NOVEMBER, 1).getTime());
        bookingService.addBooking(bookingBody, user.getId());
        assertEquals(3, bookingRepository.count());
        assertEquals(3, bookingService.getAllBooking().size());
    }

    /**
     * Check for correct total price
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void verifyTotalPrice() throws Exception {
        setAllBookingBodyOptions(bookingBody, false);
        bookingService.addBooking(bookingBody, user.getId());
        Booking noOptionsBooking = bookingService.getAllBooking().get(0);
        // 500 (base price) + 100 (deposit) - 20 (discount) = 580
        assertEquals(580, noOptionsBooking.getEndPrice());

        bookingBody.setStartBookingDate(increaseDateBy(noOptionsBooking.getEndBookingDate(), 24 * 3600));
        bookingBody.setEndBookingDate(increaseDateBy(noOptionsBooking.getEndBookingDate(), 48 * 3600));

        setAllBookingBodyOptions(bookingBody, true);
        bookingService.addBooking(bookingBody, user.getId());
        Booking allOptionsBooking = bookingService.getAllBooking().get(1);
        // 500 (base price) + 100 (deposit) - 20 (discount) + 1.5 (transport service) + 50 (wash service)
        // + 25 (sauna imp) + 5 (hand towel) + 20 (wood) = 681.5
        assertEquals(681.5, allOptionsBooking.getEndPrice());
    }

    /**
     * Helper method to set all options on a booking at once
     * @param body the booking body
     * @param enabled whether options should be enabled or not
     */
    private void setAllBookingBodyOptions(BookingBody body, boolean enabled) {
        int amount = enabled ? 1 : 0;
        body.setTransportServiceDistance(amount);
        body.setWashService(enabled);
        body.setSaunahImpAmount(amount);
        body.setHandTowelAmount(amount);
        body.setWoodAmount(amount);
    }

    /**
     * Helper method to increase date by a given number of seconds
     * @param seconds seconds to increase date
     * @return modified date
     */
    private Date increaseDateBy(Date date, long seconds) {
        Date newDate = new Date();
        newDate.setTime(date.getTime() + seconds * 1000);
        return newDate;
    }

    /**
     * This test checks if a bookingPrice can be found via it's id
     *
     * @throws Exception is thrown if no such bookingPrice is found
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getBookingPrice() throws Exception {
        assertThrows(NotFoundException.class, () -> bookingService.getBookingPrice(1));
        bookingService.addBooking(bookingBody, user.getId());
        assertNotNull(bookingService.getBookingPrice(1));
    }

    /**
     * This test checks if a bookingSauna can be found via it's id
     *
     * @throws Exception is thrown if no such bookingSauna is found
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getBookingSauna() throws Exception {
        assertThrows(NotFoundException.class, () -> bookingService.getBookingSauna(1));
        bookingService.addBooking(bookingBody, user.getId());
        assertNotNull(bookingService.getBookingSauna(1));
    }
}
