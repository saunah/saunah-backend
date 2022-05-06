package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This Interface accesses the CrudRepository.
 */
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    List<Booking> findAllBySaunaId(int saunaId);
}
