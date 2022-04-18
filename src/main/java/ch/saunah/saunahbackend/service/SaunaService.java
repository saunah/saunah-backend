package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class contains creating, removing, editing and get methods for saunas
 */
@Service
public class SaunaService {

    @Autowired
    private SaunaRepository saunaRepository;

    /**
     * Add a new Sauna to the database
     * @param saunaTypeBody the required parameters for creating a sauna
     * @return the newly created sauna object
     * @throws NullPointerException
     */
    public Sauna addSauna(SaunaTypeBody saunaTypeBody) throws NullPointerException {
        Objects.requireNonNull(saunaTypeBody, "SaunaTypeBody must not be null!");
        Objects.requireNonNull(saunaTypeBody.getName(), "Name must not be null!");
        Objects.requireNonNull(saunaTypeBody.getDescription(), "Description must not be null!");
        Objects.requireNonNull(saunaTypeBody.getLocation(), "Location must not be null!");
        Objects.requireNonNull(saunaTypeBody.getStreet(), "Street must not be null!");

        Sauna sauna = new Sauna();
        setSaunaFields(sauna, saunaTypeBody);

        return saunaRepository.save(sauna);
    }

    /**
     * Delete an existing sauna from the database
     * @param id The id of the sauna that will be deleted
     * @return if the sauna has been successfully been deleted
     */
    public String removeSauna(int id) {
        saunaRepository.deleteById(id);
        return String.format("The sauna was with id %s has been removed", id);
    }

    /**
     * Edit an already existing sauna
     * @param id the id of the sauna to be edited
     * @param saunaTypeBody the parameter that shall be changed
     * @return the sauna that has been edited
     */
    public Sauna editSauna(int id,SaunaTypeBody saunaTypeBody) {
        Sauna editSauna = getSauna(id);
        setSaunaFields(editSauna, saunaTypeBody);
        return saunaRepository.save(editSauna);

    }

    private Sauna setSaunaFields(Sauna sauna, SaunaTypeBody saunaTypeBody) {
        sauna.setName(saunaTypeBody.getName());
        sauna.setDescription(saunaTypeBody.getDescription());
        sauna.setType(saunaTypeBody.getType());
        sauna.setMaxTemp(saunaTypeBody.getMaxTemp());
        sauna.setPrize(saunaTypeBody.getPrize());
        sauna.setNumberOfPeople(saunaTypeBody.getNumberOfPeople());
        sauna.setStreet(saunaTypeBody.getStreet());
        sauna.setLocation(saunaTypeBody.getLocation());
        sauna.setPlz(saunaTypeBody.getPlz());
        return sauna;
    }

    public Sauna getSauna(int id) throws NoSuchElementException {
        if (saunaRepository.findById(id).isPresent()) {
            return saunaRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public Iterable<Sauna> getAllSauna() {
        return saunaRepository.findAll();
    }

}
