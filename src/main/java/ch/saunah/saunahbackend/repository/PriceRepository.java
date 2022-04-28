package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.Price;
import org.springframework.data.repository.CrudRepository;

/**
 * This Interface accesses the CrudRepository.
 */
public interface PriceRepository  extends CrudRepository<Price, Integer> {
}
