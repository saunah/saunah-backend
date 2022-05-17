package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.BookingSauna;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This Interface accesses the CrudRepository.
 */
public interface BookingSaunaRepository extends CrudRepository<BookingSauna, Integer> {

    /**
     * This method searches the database for all requested bookingSaunas by sauna id.
     *
     * @param saunaId the sauna id of bookingSauna
     * @return
     */
    List<BookingSauna> findAllBySaunaId(int saunaId);
}
