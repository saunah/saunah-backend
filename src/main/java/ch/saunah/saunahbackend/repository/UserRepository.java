package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * This Interface accesses the CrudRepository.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * This method searches the database for the requested email.
     * @param email the requested email
     * @return email
     */
    User findByEmail(String email);

    /**
     * This method searches the database for the requested activated ID.
     * @param activationId The requested activated ID.
     * @return activationId
     */
    User findByActivationId(String activationId);
}
