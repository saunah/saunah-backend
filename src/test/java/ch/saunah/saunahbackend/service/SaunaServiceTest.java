package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.controller.SaunaController;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.NoSuchElementException;

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
        //saunaTypeBody.setId(1);
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
    void getSauna() throws Exception{
        assertThrows(NoSuchElementException.class, () -> {
            saunaService.getSauna(10);
        });
        Sauna sauna = saunaService.addSauna(saunaTypeBody);
        assertNotNull( saunaService.getSauna(1));
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
    void removeSaunas() throws  Exception {
        Sauna sauna = saunaService.addSauna(saunaTypeBody);
        Iterable<Sauna> saunas = saunaRepository.findAll();
        assertTrue(saunas.iterator().hasNext());
        saunaService.removeSauna(1);
        saunas = saunaRepository.findAll();
        assertFalse(saunas.iterator().hasNext());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllSaunas() throws Exception {
        Sauna sauna = saunaService.addSauna(saunaTypeBody);
        Sauna sauna2 = saunaService.addSauna(saunaTypeBody);
        Sauna sauna3 = saunaService.addSauna(saunaTypeBody);
        assertEquals(3,saunaRepository.count());
        saunaService.removeSauna(1);
        assertEquals(2,saunaRepository.count());
    }


    // -- Helper methods

}
