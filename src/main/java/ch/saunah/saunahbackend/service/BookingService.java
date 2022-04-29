package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.SaunaBooking;
import ch.saunah.saunahbackend.repository.BookingRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

/**
 * This class contains the booking service methods
 */
@Service
public class BookingService {

    @Autowired
    private SaunaRepository saunaRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Returns all bookings from the database.
     *
     * @return all bookings from the database
     */
    public List<SaunaBooking> getAllBookings() {
        return (List<SaunaBooking>) bookingRepository.findAll();
    }

    /**
     * Returns the booking from the database from the specified id.
     *
     * @param id the booking id
     * @return the found booking from the database
     * @throws NotFoundException throws when no booking was found with the specified id.
     */
    public SaunaBooking getBooking(Integer id) throws NotFoundException{
        SaunaBooking saunaBooking = bookingRepository.findById(id).orElse(null);
        if (saunaBooking == null){
            throw new NotFoundException(String.format("Sauna with id %d not found!", id));
        }
        return saunaBooking;
    }

    /**
     * Add a new booking to the database
     * @param bookingBody the required parameters for creating a booking
     * @return the newly created booking object
     * @throws NullPointerException if the required object is null
     */
    public SaunaBooking addBooking(BookingBody bookingBody) throws NullPointerException {
        SaunaBooking saunaBooking = bookingRepository.save(new SaunaBooking());
        return null;
    }

    private int calculatePrice(BookingBody bookingBody, int endPrice){
        return endPrice;
    }
}
