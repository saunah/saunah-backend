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
        Sauna sauna = saunaService.addSauna(saunaTypeBody);
        Iterable<Sauna> saunas = saunaRepository.findAll();
        assertTrue(saunas.iterator().hasNext());
        assertThrows(NullPointerException.class, () -> saunaService.addSauna(null));
        assertThrows(NullPointerException.class, () -> saunaService.addSauna(new SaunaTypeBody()));
        Sauna sauna2 = saunaService.addSauna(saunaTypeBody);
        assertNotEquals(sauna.getId(), sauna2.getId());

        assertEquals(saunaTypeBody.getName(), sauna.getName());
        assertEquals(saunaTypeBody.getDescription(), sauna.getDescription());
        assertEquals(saunaTypeBody.getMaxTemp(), sauna.getMaxTemp());
        assertEquals(saunaTypeBody.getNumberOfPeople(), sauna.getNumberOfPeople());
        assertEquals(saunaTypeBody.getPrize(), sauna.getPrize());
        assertEquals(saunaTypeBody.getLocation(), sauna.getLocation());
        assertEquals(saunaTypeBody.getStreet(), sauna.getStreet());
        assertEquals(saunaTypeBody.getType(), sauna.getType());
        assertEquals(saunaTypeBody.getPlz(), sauna.getPlz());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getSauna() {
        //assertNull( saunaService.getSauna(notExistingId));
        //todo add sauna
        //assertNotNull( saunaService.getSauna(existing));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editSauna() {
        //eddit non existing
        //edit existing
        //check if all values are equal
        //check null pointer exceptions
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removeSaunas() {
        //try remove non exsting sauna
        //todo add sauna
        //try remove existing sauna
        //Todo; check if sauna does not exist

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllSaunas() {
        //add random number of saunas
        //check if database has amount of saunas
        //ermove 1
        //-> check if database has 1 less sauna than before
    }


    // -- Helper methods

}
