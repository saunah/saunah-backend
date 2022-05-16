package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.Booking;
import org.springframework.data.repository.CrudRepository;

/**
 * This Interface accesses the CrudRepository.
 */
public interface BookingRepository extends CrudRepository<Booking, Integer> {
}
