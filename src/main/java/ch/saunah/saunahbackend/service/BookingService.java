package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;

/**
 * This class contains the booking service methods
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Add a new booking to the database
     *
     * @param bookingBody the required parameters for creating a booking
     * @return the newly created booking object
     * @throws NullPointerException if the required object is null
     */
    public Booking addBooking(BookingBody bookingBody) throws NullPointerException {
        Objects.requireNonNull(bookingBody, "BookingBody must not be null!");
        Booking booking = new Booking();
        booking.setState(BookingState.OPENED);
        setBookingFields(booking, bookingBody);

        return bookingRepository.save(booking);
    }

    /**
     * Lets the user edit an existing booking structure
     *
     * @param id          The id of the booking structure that shall be edited
     * @param bookingBody the parameters of the booking structure that is being edited
     * @return The booking structure with the changed values
     * @throws NullPointerException if no booking structure exists
     */
    public Booking editBooking(int id, BookingBody bookingBody) throws NullPointerException {
        Booking booking = getBooking(id);
        setBookingFields(booking, bookingBody);
        return bookingRepository.save(booking);
    }

    /**
     * Approves a booking structure from the database
     *
     * @param id the id of the booking structure that should be approved
     * @throws NotFoundException if no such booking structure exists
     */
    public void approveBooking(int id) throws NotFoundException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking structure with id %d not found!", id));
        }
        booking.setState(BookingState.APPROVED);
        bookingRepository.save(booking);
    }

    /**
     * Cancels a booking structure from the database
     *
     * @param id the id of the booking structure that should be canceled
     * @throws NotFoundException if no such booking structure exists
     */
    public void cancelBooking(int id) throws NotFoundException {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new NotFoundException(String.format("Booking structure with id %d not found!", id));
        }
        booking.setState(BookingState.CANCELED);
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
            throw new NotFoundException(String.format("Sauna with id %d not found!", id));
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

    private Booking setBookingFields(Booking booking, BookingBody bookingBody) {
        booking.setSaunaName(bookingBody.getSaunaName());
        booking.setStartBookingDate(bookingBody.getStartBookingDate());
        booking.setEndBookingDate(bookingBody.getEndBookingDate());
        booking.setCreation(bookingBody.getCreation());
        booking.setUserId(bookingBody.getUserID());
        booking.setSaunaId(bookingBody.getSaunaId());
        booking.setLocation(bookingBody.getLocation());
        booking.setTransportService(bookingBody.isTransportService());
        booking.setWashService(booking.isWashService());
        booking.setSaunahImp(bookingBody.isSaunahImp());
        booking.setDeposit(bookingBody.isDeposit());
        booking.setHandTowel(bookingBody.isHandTowel());
        booking.setWood(bookingBody.isWood());
        return booking;
    }
}
