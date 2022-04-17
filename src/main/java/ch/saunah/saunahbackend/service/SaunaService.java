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

        return saunaRepository.save(sauna);
    }



}
