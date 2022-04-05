package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
