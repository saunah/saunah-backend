package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class SaunaService {

    @Autowired
    private SaunaRepository saunaRepository;

    public Sauna addSauna(SaunaTypeBody saunaTypeBody) throws NullPointerException {
        Objects.requireNonNull(saunaTypeBody, "SaunaTypeBody must not be null!");
        Objects.requireNonNull(saunaTypeBody.getName(), "Name must not be null!");
        Objects.requireNonNull(saunaTypeBody.getDescription(), "Description must not be null!");
        Objects.requireNonNull(saunaTypeBody.getLocation(), "Location must not be null!");
        Objects.requireNonNull(saunaTypeBody.getStreet(), "Street must not be null!");
        Sauna sauna = new Sauna();
        sauna = setSaunaFields(sauna,saunaTypeBody);


        return saunaRepository.save(sauna);
    }

    public Sauna getSauna(int id) throws NoSuchElementException {
        return saunaRepository.findById(id).get();
    }

    public Iterable<Sauna> getAllSauna() {
        return saunaRepository.findAll();
    }

    public String removeSauna(int id) throws Exception{
        saunaRepository.deleteById(id);
        return String.format("The sauna was with id %s has been removed", id);
    }

    public Sauna editSauna(@RequestParam("Id") int id,SaunaTypeBody saunaTypeBody) {
        Sauna editSauna = getSauna(id);
        editSauna = setSaunaFields(editSauna, saunaTypeBody);
        return saunaRepository.save(editSauna);

    }

    private Sauna setSaunaFields(Sauna sauna, SaunaTypeBody saunaTypeBody) {
        sauna.setName(saunaTypeBody.getName());
        sauna.setDescription(saunaTypeBody.getDescription());
        sauna.setPicture(saunaTypeBody.getPicture());
        sauna.setType(saunaTypeBody.getType());
        sauna.setMaxTemp(saunaTypeBody.getMaxTemp());
        sauna.setPrize(saunaTypeBody.getPrize());
        sauna.setNumberOfPeople(saunaTypeBody.getNumberOfPeople());
        sauna.setStreet(saunaTypeBody.getStreet());
        sauna.setLocation(saunaTypeBody.getLocation());
        sauna.setPlz(saunaTypeBody.getPlz());
        return sauna;
    }


}
