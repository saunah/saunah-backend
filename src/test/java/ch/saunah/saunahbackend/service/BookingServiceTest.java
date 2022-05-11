package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.*;
import ch.saunah.saunahbackend.repository.BookingRepository;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import ch.saunah.saunahbackend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.webjars.NotFoundException;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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
    private BookingBody bookingBody = null;

    @BeforeEach
    void setUp() {
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
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 20));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 1));
        bookingBody.setUserId(user.getId());
        bookingBody.setSaunaId(sauna.getId());
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
    void addNewBooking() throws Exception {
        Booking booking = bookingService.addBooking(bookingBody);
        Iterable<Booking> bookings = bookingRepository.findAll();
        assertTrue(bookings.iterator().hasNext());
        assertThrows(NullPointerException.class, () -> bookingService.addBooking(null));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.MAY, 3));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.SEPTEMBER, 10));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 10));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.AUGUST, 30));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 25));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 30));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 10));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 30));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 20));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.AUGUST, 25));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 15));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 1));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.AUGUST, 20));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 1));
        assertThrows(Exception.class, () -> bookingService.addBooking(bookingBody));
        bookingBody.setStartBookingDate(new Date(2022, Calendar.SEPTEMBER, 10));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.SEPTEMBER, 30));
        Booking booking2 = bookingService.addBooking(bookingBody);
        assertNotEquals(booking.getId(), booking2.getId());
    }

    /**
     * This test checks if a booking can be approved
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void approveBooking() throws Exception {
        assertThrows(NotFoundException.class, () -> bookingService.approveBooking(1));
        Booking booking = bookingService.addBooking(bookingBody);
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
        Booking booking = bookingService.addBooking(bookingBody);
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
        bookingService.addBooking(bookingBody);
        assertNotNull(bookingService.getBooking(1));
    }

    /**
     * This test checks if all bookings can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllBooking() throws Exception {
        bookingService.addBooking(bookingBody);
        bookingBody.setStartBookingDate(new Date(2022, Calendar.SEPTEMBER, 25));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.OCTOBER, 1));
        bookingService.addBooking(bookingBody);
        bookingBody.setStartBookingDate(new Date(2022, Calendar.OCTOBER, 25));
        bookingBody.setEndBookingDate(new Date(2022, Calendar.NOVEMBER, 1));
        bookingService.addBooking(bookingBody);
        assertEquals(3, bookingRepository.count());
    }
}
