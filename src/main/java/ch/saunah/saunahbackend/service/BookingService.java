package ch.saunah.saunahbackend.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.BookingRepository;
import ch.saunah.saunahbackend.repository.PriceRepository;

/**
 * This class contains the booking service methods
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private SaunaService saunaService;

    @Autowired
    private GoogleCalendarService calendarService;

    /**
     * Add a new booking to the database and assigns the end price to it.
     *
     * @param bookingBody the required parameters for creating a booking
     * @return the newly created booking object
     * @throws NullPointerException if the required object is null
     */
    public Booking addBooking(BookingBody bookingBody, int userId) throws Exception {
        Objects.requireNonNull(bookingBody, "BookingBody must not be null!");
        Objects.requireNonNull(bookingBody.getEndBookingDate(), "EndBookingDate must not be null!");
        Objects.requireNonNull(bookingBody.getStartBookingDate(), "StartBookingDate must not be null!");
        Objects.requireNonNull(bookingBody.getLocation(), "Location must not be null!");
        Price price = priceRepository.findAll().iterator().next();
        if (price == null) {
            throw new NotFoundException("No Price available in the database!");
        }
        Date now = new Date(System.currentTimeMillis());
        if (now.after(bookingBody.getStartBookingDate())) {
            throw new IllegalArgumentException("Booking date is in the past");
        }
        if (bookingBody.getStartBookingDate().after(bookingBody.getEndBookingDate())) {
            throw new IllegalArgumentException("Invalid start date");
        }
        List<Booking> allSaunaBookings = bookingRepository.findAllBySaunaId(bookingBody.getSaunaId());
        if (allSaunaBookings.stream().anyMatch(x -> dateRangeCollide(bookingBody.getStartBookingDate(),
            bookingBody.getEndBookingDate(), x.getStartBookingDate(), x.getEndBookingDate()))) {
            throw new IllegalArgumentException("Sauna is not available during this date range");
        }

        Sauna sauna = saunaService.getSauna(bookingBody.getSaunaId());
        Booking booking = new Booking();
        booking.setStartBookingDate(bookingBody.getStartBookingDate());
        booking.setEndBookingDate(bookingBody.getEndBookingDate());
        booking.setUserId(userId);
        booking.setLocation(bookingBody.getLocation());
        booking.setTransportService(bookingBody.isTransportService());
        booking.setWashService(bookingBody.isWashService());
        booking.setSaunahImp(bookingBody.isSaunahImp());
        booking.setDeposit(bookingBody.isDeposit());
        booking.setHandTowel(bookingBody.isHandTowel());
        booking.setWood(bookingBody.isWood());
        booking.setCreation(new Date(System.currentTimeMillis()));
        booking.setEndPrice(calculatePrice(bookingBody, sauna, price));
        booking.setSaunaId(bookingBody.getSaunaId());
        booking.setSaunaName(sauna.getName());
        booking.setSaunaDescription(sauna.getDescription());
        booking.setSaunaIsMobile(sauna.isMobile());
        booking.setSaunaPrice(sauna.getPrice());
        booking.setSaunaMaxTemp(sauna.getMaxTemp());
        booking.setSaunaNumberOfPeople(sauna.getNumberOfPeople());
        booking.setSaunaLocation(sauna.getLocation());
        booking.setSaunaStreet(sauna.getStreet());
        booking.setSaunaZip(sauna.getZip());
        booking.setSaunaType(sauna.getType());
        booking.setState(BookingState.OPENED);
        booking.setGoogleEventID(calendarService.createEvent(sauna.getGoogleCalenderID(), booking));

        return bookingRepository.save(booking);
    }

    private boolean dateRangeCollide(Date start, Date end, Date startExisting, Date endExisting) {
        return start.getTime() >= startExisting.getTime() && start.getTime() <= endExisting.getTime() ||
            end.getTime() >= startExisting.getTime() && end.getTime() <= endExisting.getTime() ||
            start.getTime() < startExisting.getTime() && end.getTime() > endExisting.getTime();
    }

    /**
     * Approves a booking structure from the database
     *
     * @param id the id of the booking structure that should be approved
     * @throws NotFoundException if no such booking structure exists
     * @throws IOException if Google Calendar event could not be updated
     */
    public void approveBooking(int id) throws NotFoundException, IOException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking structure with id %d not found!", id));
        }
        booking.setState(BookingState.APPROVED);

        Sauna sauna = saunaService.getSauna(booking.getSaunaId());
        calendarService.approveEvent(sauna.getGoogleCalenderID(), booking.getGoogleEventID());

        bookingRepository.save(booking);
    }

    /**
     * Cancels a booking structure from the database
     *
     * @param id the id of the booking structure that should be canceled
     * @throws NotFoundException if no such booking structure exists
     * @throws IOException if Google Calendar event could not be updated
     */
    public void cancelBooking(int id) throws NotFoundException, IOException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking structure with id %d not found!", id));
        }
        booking.setState(BookingState.CANCELED);

        Sauna sauna = saunaService.getSauna(booking.getSaunaId());
        calendarService.deleteEvent(sauna.getGoogleCalenderID(), booking.getGoogleEventID());

        bookingRepository.save(booking);
    }

    /**
     * Returns the booking from the database from the specified id.
     *
     * @param id the booking id
     * @return the found booking from the database
     * @throws NotFoundException throws when no booking was found with the specified id.
     */
    public Booking getBooking(Integer id) throws NotFoundException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking with id %d not found!", id));
        }
        return booking;
    }

    /**
     * Returns all bookings from the database.
     *
     * @return all bookings from the database
     */
    public List<Booking> getAllBooking() {
        return (List<Booking>) bookingRepository.findAll();
    }

    private double calculatePrice(BookingBody bookingBody, Sauna sauna, Price price) {
        double endPrice = 0;
        endPrice += sauna.getPrice();
        if (bookingBody.isTransportService()) {
            endPrice += price.getTransportService();
        }
        if (bookingBody.isWashService()) {
            endPrice += price.getWashService();
        }
        if (bookingBody.isSaunahImp()) {
            endPrice += price.getSaunahImp();
        }
        if (bookingBody.isDeposit()) {
            endPrice += price.getDeposit();
        }
        if (bookingBody.isHandTowel()) {
            endPrice += price.getHandTowel();
        }
        if (bookingBody.isWood()) {
            endPrice += price.getWood();
        }

        return endPrice;
    }
}
