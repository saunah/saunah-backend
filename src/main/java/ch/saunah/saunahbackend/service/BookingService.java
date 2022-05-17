package ch.saunah.saunahbackend.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingPrice;
import ch.saunah.saunahbackend.model.BookingSauna;
import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.BookingPriceRepository;
import ch.saunah.saunahbackend.repository.BookingSaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.dto.BookingBody;
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
    private BookingSaunaRepository bookingSaunaRepository;

    @Autowired
    private BookingPriceRepository bookingPriceRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private SaunaService saunaService;

    @Autowired
    private GoogleCalendarService calendarService;

    /**
     * Adds a new booking to the database and assigns the end price to it.
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
        validateBookingData(bookingBody);
        Booking booking = new Booking();
        setBookingFields(booking, bookingBody, true, false);
        booking.setUserId(userId);
        bookingRepository.save(booking);
        BookingPrice bookingPrice = createBookingPrice(bookingBody);
        bookingPriceRepository.save(bookingPrice);
        BookingSauna bookingSauna = createBookingSauna(bookingBody);
        bookingSaunaRepository.save(bookingSauna);
        booking.setBookingPrice(bookingPrice);
        booking.setBookingSauna(bookingSauna);
        booking.setEndPrice(calculatePrice(booking, bookingSauna, bookingPrice));
        return bookingRepository.save(booking);
    }

    private void validateBookingData(BookingBody bookingBody) {
        Date now = new Date(System.currentTimeMillis());
        if (now.after(bookingBody.getStartBookingDate())) {
            throw new IllegalArgumentException("Booking date is in the past");
        }
        if (bookingBody.getStartBookingDate().after(bookingBody.getEndBookingDate())) {
            throw new IllegalArgumentException("Invalid start date");
        }
    }

    private void setBookingFields(Booking booking, BookingBody bookingBody, boolean setDefaultValues, boolean isAdmin) throws IOException {
        Sauna sauna = saunaService.getSauna(bookingBody.getSaunaId());
        booking.setStartBookingDate(bookingBody.getStartBookingDate());
        booking.setEndBookingDate(bookingBody.getEndBookingDate());
        booking.setLocation(bookingBody.getLocation());
        booking.setTransportServiceDistance(bookingBody.getTransportServiceDistance());
        booking.setWashService(bookingBody.isWashService());
        booking.setSaunahImpAmount(bookingBody.getSaunahImpAmount());
        booking.setHandTowelAmount(bookingBody.getHandTowelAmount());
        booking.setWoodAmount(bookingBody.getWoodAmount());
        booking.setState(BookingState.OPENED);
        booking.setComment(bookingBody.getComment());
        booking.setGoogleEventID(calendarService.createEvent(sauna.getGoogleCalendarId(), booking));

        if (setDefaultValues) {
            // set default values
            booking.setCreation(new Date(System.currentTimeMillis()));
            booking.setDeposit(true);
            booking.setDiscount(0);
            booking.setDiscountDescription(null);
        } else if (isAdmin){
            // let admin edit
            booking.setDeposit(bookingBody.isDeposit());
            booking.setDiscount(bookingBody.getDiscount());
            booking.setDiscountDescription(bookingBody.getDiscountDescription());
        } // else ignore
    }

    private BookingPrice createBookingPrice(BookingBody bookingBody) {
        Price price = priceRepository.findAll().iterator().next();
        if (price == null) {
            throw new NotFoundException("No Price available in the database!");
        }
        BookingPrice bookingPrice = new BookingPrice();
        bookingPrice.setTransportServicePrice(price.getTransportService());
        bookingPrice.setWashServicePrice(price.getWashService());
        bookingPrice.setSaunahImpPrice(price.getSaunahImp());
        bookingPrice.setDepositPrice(price.getDeposit());
        bookingPrice.setHandTowelPrice(price.getHandTowel());
        bookingPrice.setWoodPrice(price.getWood());
        return bookingPrice;
    }

    private BookingSauna createBookingSauna(BookingBody bookingBody) {
        Sauna sauna = saunaService.getSauna(bookingBody.getSaunaId());
        BookingSauna bookingSauna = new BookingSauna();
        bookingSauna.setSaunaId(bookingBody.getSaunaId());
        bookingSauna.setSaunaName(sauna.getName());
        bookingSauna.setSaunaDescription(sauna.getDescription());
        bookingSauna.setSaunaIsMobile(sauna.isMobile());
        bookingSauna.setSaunaPrice(sauna.getPrice());
        bookingSauna.setSaunaMaxTemp(sauna.getMaxTemp());
        bookingSauna.setSaunaNumberOfPeople(sauna.getNumberOfPeople());
        bookingSauna.setSaunaLocation(sauna.getLocation());
        bookingSauna.setSaunaStreet(sauna.getStreet());
        bookingSauna.setSaunaZip(sauna.getZip());
        bookingSauna.setSaunaType(sauna.getType());
        return bookingSauna;
    }

    /**
     * Edit an already existing booking
     *
     * @param bookingId   the id of the booking to be edited
     * @param bookingBody the parameter that shall be changed
     * @return the booking that has been edited
     */
    public Booking editBooking(int bookingId, BookingBody bookingBody) throws IOException {
        Objects.requireNonNull(bookingBody, "BookingBody must not be null!");
        Objects.requireNonNull(bookingBody.getEndBookingDate(), "EndBookingDate must not be null!");
        Objects.requireNonNull(bookingBody.getStartBookingDate(), "StartBookingDate must not be null!");
        Objects.requireNonNull(bookingBody.getLocation(), "Location must not be null!");
        validateBookingData(bookingBody);
        Booking editBooking = getBooking(bookingId);
        setBookingFields(editBooking, bookingBody, false, true);
        return bookingRepository.save(editBooking);
    }

    /**
     * Approves a booking structure from the database
     *
     * @param id the id of the booking structure that should be approved
     * @throws NotFoundException if no such booking structure exists
     * @throws IOException       if Google Calendar event could not be updated
     */
    public void approveBooking(int id) throws NotFoundException, IOException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking structure with id %d not found!", id));
        }
        booking.setState(BookingState.APPROVED);

        Sauna sauna = saunaService.getSauna(id);
        calendarService.approveEvent(sauna.getGoogleCalendarId(), booking.getGoogleEventID());

        bookingRepository.save(booking);
    }

    /**
     * Cancels a booking structure from the database
     *
     * @param id the id of the booking structure that should be canceled
     * @throws NotFoundException if no such booking structure exists
     * @throws IOException       if Google Calendar event could not be updated
     */
    public void cancelBooking(int id) throws NotFoundException, IOException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking structure with id %d not found!", id));
        }
        booking.setState(BookingState.CANCELED);

        Sauna sauna = saunaService.getSauna(id);
        calendarService.deleteEvent(sauna.getGoogleCalendarId(), booking.getGoogleEventID());

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
     * Returns the bookingPrice from the database from the specified id.
     *
     * @param id the bookingPrice id
     * @return the found bookingPrice from the database
     * @throws NotFoundException throws when no bookingPrice was found with the specified id.
     */
    public BookingPrice getBookingPrice(Integer id) throws NotFoundException {
        BookingPrice bookingPrice = bookingPriceRepository.findById(id).orElse(null);
        if (bookingPrice == null) {
            throw new NotFoundException(String.format("Booking with id %d not found!", id));
        }
        return bookingPrice;
    }

    /**
     * Returns the bookingSauna from the database from the specified id.
     *
     * @param id the bookingSauna id
     * @return the found bookingSauna from the database
     * @throws NotFoundException throws when no bookingSauna was found with the specified id.
     */
    public BookingSauna getBookingSauna(Integer id) throws NotFoundException {
        BookingSauna bookingSauna = bookingSaunaRepository.findById(id).orElse(null);
        if (bookingSauna == null) {
            throw new NotFoundException(String.format("Booking with id %d not found!", id));
        }
        return bookingSauna;
    }

    /**
     * Returns all bookings from the database.
     *
     * @return all bookings from the database
     */
    public List<Booking> getAllBooking() {
        return (List<Booking>) bookingRepository.findAll();
    }

    private double calculatePrice(Booking booking, BookingSauna bookingSauna, BookingPrice bookingPrice) {
        double endPrice = 0;
        // TODO: Calculate endPrice like in frontend.
        return endPrice;
    }
}
