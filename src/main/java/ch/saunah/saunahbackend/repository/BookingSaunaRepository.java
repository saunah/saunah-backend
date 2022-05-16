package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingSauna;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This Interface accesses the CrudRepository.
 */
public interface BookingSaunaRepository extends CrudRepository<BookingSauna, Integer> {

    BookingSauna findByBookingId(int saunaId);

    List<BookingSauna> findAllBySaunaId(int saunaId);
}
