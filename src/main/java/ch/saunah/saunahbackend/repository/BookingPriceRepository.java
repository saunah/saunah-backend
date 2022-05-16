package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.BookingPrice;
import org.springframework.data.repository.CrudRepository;

/**
 * This Interface accesses the CrudRepository.
 */
public interface BookingPriceRepository extends CrudRepository<BookingPrice, Integer> {

}
