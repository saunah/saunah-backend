package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class SaunaService {

    @Autowired
    private SaunaRepository saunaRepository;

    public Sauna addSauna(long id, SaunaTypeBody saunaTypeBody) throws Exception {

        Sauna sauna = new Sauna();

        sauna.setId(id);
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
