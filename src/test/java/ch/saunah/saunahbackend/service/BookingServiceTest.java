package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.repository.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.webjars.NotFoundException;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaunahBackendApplication.class)
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;
    private BookingBody bookingBody = null;

    @BeforeEach
    void setUp() {
        bookingBody = new BookingBody();
        bookingBody.setSaunaName("Saunah_01");
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 20));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 1));
        bookingBody.setCreation(new Date(2022, Calendar.AUGUST, 1));
        bookingBody.setUserID(1);
        bookingBody.setSaunaId(1);
        bookingBody.setLocation("Zürich");
        bookingBody.setTransportService(true);
        bookingBody.setWashService(true);
        bookingBody.setSaunahImp(false);
        bookingBody.setDeposit(true);
        bookingBody.setHandTowel(false);
        bookingBody.setWood(true);
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * This test checks if the user can create a new booking structure
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewBooking() {
        Booking booking = bookingService.addBooking(bookingBody);
        Iterable<Booking> bookings = bookingRepository.findAll();
        assertTrue(bookings.iterator().hasNext());
        assertThrows(NullPointerException.class, () -> bookingService.addBooking(null));
        Booking booking2 = bookingService.addBooking(bookingBody);
        assertNotEquals(booking.getId(), booking2.getId());
    }

    /**
     * This test checks if the user can edit a booking structure
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editBooking() {

    }

    /**
     * This test Checks if a booking can be approved
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void approveBooking() {
        Booking booking = bookingService.addBooking(bookingBody);
        bookingService.approveBooking(booking.getId());
        assertEquals(BookingState.APPROVED, booking.getState());
    }

    /**
     * This test Checks if a booking can be canceled
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void cancelBooking() {
        Booking booking = bookingService.addBooking(bookingBody);
        bookingService.cancelBooking(booking.getId());
        assertEquals(BookingState.CANCELED, booking.getState());
    }

    /**
     * This test checks if a booking can be found via it's id
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getBooking() {
        assertThrows(NotFoundException.class, () -> bookingService.getBooking(1));
        bookingService.addBooking(bookingBody);
        assertNotNull(bookingService.getBooking(1));
    }

    /**
     * This test checks if all bookings can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllBooking() {
        bookingService.addBooking(bookingBody);
        bookingService.addBooking(bookingBody);
        bookingService.addBooking(bookingBody);
        assertEquals(3, bookingRepository.count());
    }
}
