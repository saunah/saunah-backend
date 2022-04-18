package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.controller.SaunaController;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.webjars.NotFoundException;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests all sauna service methods
 */
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
        saunaTypeBody.setName("Mobile Sauna 1");
        saunaTypeBody.setDescription("Eine Mobile Sauna");
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

    /**
     * This test checks if the user can create a new sauna with the correct value for the fields
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewSauna() {
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

    /**
     * This test, checks if a sauna can be found via it's id
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getSauna() {
        assertThrows(NotFoundException.class, () -> saunaService.getSauna(1));
        saunaService.addSauna(saunaTypeBody);
        assertNotNull(saunaService.getSauna(1));
    }

    /**
     * This test checks if the fields values of an existing sauna can be edited
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editSauna() {
        Sauna sauna = saunaService.addSauna(saunaTypeBody);
        SaunaTypeBody saunaTypeBodyChanged = new SaunaTypeBody();
        saunaTypeBodyChanged.setName("Sauna 3");
        saunaTypeBodyChanged.setDescription("Eine nicht Mobile Sauna");
        saunaTypeBodyChanged.setType(false);
        saunaTypeBodyChanged.setPrize(1000);
        saunaTypeBodyChanged.setMaxTemp(48);
        saunaTypeBodyChanged.setNumberOfPeople(15);
        saunaTypeBodyChanged.setLocation("Winterthur");
        saunaTypeBodyChanged.setStreet("Bahnhofstrasse 6");
        saunaTypeBodyChanged.setPlz(10000);
        sauna = saunaService.editSauna(sauna.getId(), saunaTypeBodyChanged);
        checkSaunaFields(saunaTypeBodyChanged, sauna);
    }

    /**
     * This test check if an existing sauna can be deleted from the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removeSaunas() {
        saunaService.addSauna(saunaTypeBody);
        Iterable<Sauna> saunas = saunaRepository.findAll();
        assertTrue(saunas.iterator().hasNext());
        saunaService.removeSauna(1);
        saunas = saunaRepository.findAll();
        assertFalse(saunas.iterator().hasNext());
    }

    /**
     * This test checks if all saunas can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllSaunas() {
        saunaService.addSauna(saunaTypeBody);
        saunaService.addSauna(saunaTypeBody);
        saunaService.addSauna(saunaTypeBody);
        assertEquals(3,saunaRepository.count());
        saunaService.removeSauna(1);
        assertEquals(2,saunaRepository.count());
    }

    /**
     * This method helps checking if the values are correct
     * @param saunaTypeBody The sauna parameters
     * @param sauna the instance of a sauna
     */
    private void checkSaunaFields(SaunaTypeBody saunaTypeBody, Sauna sauna) {
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
}
