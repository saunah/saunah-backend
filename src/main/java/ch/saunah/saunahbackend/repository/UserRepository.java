package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * This Interface accesses the CrudRepository.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
