package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.AuthenticationToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationTokenRepository extends CrudRepository<AuthenticationToken, Integer> {
}
