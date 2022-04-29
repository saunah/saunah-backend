package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaBooking;
import ch.saunah.saunahbackend.repository.BookingRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public SaunaBooking addBooking(BookingBody bookingBody) {
        SaunaBooking saunaBooking = bookingRepository.save(new SaunaBooking());
        return null;
    }

    /**
     * Returns all bookings from the database.
     *
     * @return all bookings from the database
     */
    public List<SaunaBooking> getAllBookings() {
        return (List<SaunaBooking>) bookingRepository.findAll();
    }

    private int calculatePrice(BookingBody bookingBody, int endPrice){
        return endPrice;
    }
}
