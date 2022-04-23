package ch.saunah.saunahbackend.repository;

import ch.saunah.saunahbackend.model.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository  extends CrudRepository<Price, Integer> {
}
