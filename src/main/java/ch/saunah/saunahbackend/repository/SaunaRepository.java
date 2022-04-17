package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.Sauna;
import org.springframework.data.repository.CrudRepository;

/**
 * This Interface accesses the CrudRepository.
 */
public interface SaunaRepository extends CrudRepository<Sauna, Integer> {

}
