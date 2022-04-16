package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.controller.SaunaController;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.dto.SignUpBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaunahBackendApplication.class)
public class SaunaServiceTest {

    @Autowired
    private SaunaService saunaService;
    @Autowired
    private SaunaRepository saunaRepository;
    private SaunaTypeBody saunaTypeBody = null;

    @Autowired
    private SaunaController saunaController;

    @BeforeEach
    void setUp() {
        saunaTypeBody = new SaunaTypeBody();
        saunaTypeBody.setId(1);
        saunaTypeBody.setName("Mobile Sauna 1");
        saunaTypeBody.setDescription("Eine Mobile Sauna");
        saunaTypeBody.setPicture(true);
        saunaTypeBody.setType(true);
        saunaTypeBody.setPrize(500);
        saunaTypeBody.setMaxTemp(51);
        saunaTypeBody.setNumberOfPeople(12);
        saunaTypeBody.setLocation("Zürich");
        saunaTypeBody.setStreet("Bahnhofstrasse 5");
        saunaTypeBody.setPlz(9000);
    }

    @AfterEach
    void tearDown() {

    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewSauna() throws Exception {
        saunaService.addSauna(1, saunaTypeBody);
        Iterable<Sauna> saunas = saunaRepository.findAll();
        assertTrue(saunas.iterator().hasNext());
    }

    @Test
    void getSauna() {

    }

    @Test
    void removeSaunas() {

    }

    @Test
    void getAllSaunas() {

    }


    // -- Helper methods

    /**
     * Create a new Sauna Type
     */
    private boolean createNewSauna(int id) {
        Sauna newSauna = new Sauna(id, "MobileSauna", "a New type of Sauna for relaxing", true,
            true, 150, 51, 4,"Zürich", "Bahnhofstrasse 4", 9000);
        saunaRepository.save(newSauna);
        return true;
    }

    /**
     * Example helper method to get size of an iterable.
     */
    private <T> long getIterableSize(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }
}
