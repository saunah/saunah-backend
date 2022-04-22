package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.SaunaImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This Interface accesses the CrudRepository.
 */
public interface SaunaImageRepository extends CrudRepository<SaunaImage, Integer> {
    /**
     * This method searches the database for images of the sauna.
     *
     * @param id the id of the sauna
     * @return List of SaunaImage of the sauna id
     */
    List<SaunaImage> findBySaunaId(int id);
}
