package ch.saunah.saunahbackend.model;

import org.springframework.data.repository.CrudRepository;

public interface SaunaRepository extends CrudRepository<User, Integer> {

}
