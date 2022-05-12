package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * This Interface accesses the CrudRepository.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * This method searches the database for the requested email.
     *
     * @param email the requested email
     * @return email
     */
    User findByEmail(String email);

    /**
     * This method searches the database for the requested activated ID.
     *
     * @param activationId The requested activated ID.
     * @return activationId
     */
    User findByActivationId(String activationId);

    /**
     * This method searches the database for the requested user roles.
     *
     * @param role The requested user role.
     * @return role
     */
    List<User> findByRole(UserRole role);
}
