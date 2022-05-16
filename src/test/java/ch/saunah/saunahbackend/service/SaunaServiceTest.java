package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.saunah.saunahbackend.configuration.SaunahTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaImage;
import ch.saunah.saunahbackend.repository.SaunaRepository;

/**
 * This class tests all sauna service methods
 */
@SpringBootTest(classes = {SaunahBackendApplication.class, SaunahTestConfig.class} )
class SaunaServiceTest {

    @Autowired
    private SaunaService saunaService;
    @Autowired
    private SaunaRepository saunaRepository;
    private SaunaTypeBody saunaTypeBody = null;

    @BeforeEach
    void setUp() {
        saunaTypeBody = new SaunaTypeBody();
        saunaTypeBody.setName("Mobile Sauna 1");
        saunaTypeBody.setDescription("Eine Mobile Sauna");
        saunaTypeBody.setMobile(true);
        saunaTypeBody.setPrice(500);
        saunaTypeBody.setMaxTemp(51);
        saunaTypeBody.setNumberOfPeople(12);
        saunaTypeBody.setLocation("Zürich");
        saunaTypeBody.setStreet("Bahnhofstrasse 5");
        saunaTypeBody.setZip(9000);
        saunaTypeBody.setType("Tent");
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
        assertEquals(saunaTypeBody.getPrice(), sauna.getPrice());
        assertEquals(saunaTypeBody.getLocation(), sauna.getLocation());
        assertEquals(saunaTypeBody.getStreet(), sauna.getStreet());
        assertEquals(saunaTypeBody.isMobile(), sauna.isMobile());
        assertEquals(saunaTypeBody.getZip(), sauna.getZip());
        assertEquals(saunaTypeBody.getType(), sauna.getType());
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
        saunaTypeBodyChanged.setMobile(false);
        saunaTypeBodyChanged.setPrice(1000);
        saunaTypeBodyChanged.setMaxTemp(48);
        saunaTypeBodyChanged.setNumberOfPeople(15);
        saunaTypeBodyChanged.setLocation("Winterthur");
        saunaTypeBodyChanged.setStreet("Bahnhofstrasse 6");
        saunaTypeBodyChanged.setZip(10000);
        saunaTypeBodyChanged.setType("TENT");
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
        assertEquals(saunaTypeBody.getPrice(), sauna.getPrice());
        assertEquals(saunaTypeBody.getLocation(), sauna.getLocation());
        assertEquals(saunaTypeBody.getStreet(), sauna.getStreet());
        assertEquals(saunaTypeBody.isMobile(), sauna.isMobile());
        assertEquals(saunaTypeBody.getZip(), sauna.getZip());
    }


    /**
     * This test checks if images can be added to a sauna successfully.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addSaunaImage() throws IOException{
        assertThrows(NotFoundException.class, ()-> saunaService.addSaunaImages(0, null));
        saunaService.addSauna(saunaTypeBody);
        Sauna sauna = saunaService.getAllSauna().get(0);
        assertThrows(NullPointerException.class, ()-> saunaService.addSaunaImages(sauna.getId(), null));
        List<SaunaImage> images = saunaService.getSaunaImages(sauna.getId());
        assertTrue(images.isEmpty());
        saunaService.addSaunaImages(sauna.getId(), new ArrayList<>());
        images = saunaService.getSaunaImages(sauna.getId());
        assertTrue(images.isEmpty());
        List<MultipartFile> testfiles = new ArrayList<>();
        testfiles.add(new MockMultipartFile("test", new byte[0]));
        saunaService.addSaunaImages(sauna.getId(), testfiles);
        assertEquals(testfiles.size(), saunaService.getSaunaImages(sauna.getId()).size());
        testfiles.clear();
        testfiles.add(new MockMultipartFile("test", new byte[0]));
        testfiles.add(new MockMultipartFile("test", new byte[0]));
        saunaService.addSaunaImages(sauna.getId(), testfiles);
        int savedImagesCount = saunaService.getSaunaImages(sauna.getId()).size();
        assertEquals(3, savedImagesCount);
    }

    /**
     * This test checks if an added image can be removed from the database.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removeSaunaImage()throws IOException {
        assertThrows(NotFoundException.class, () -> saunaService.removeSaunaImage(1));
        saunaService.addSauna(saunaTypeBody);
        Sauna sauna = saunaService.getAllSauna().get(0);
        List<MultipartFile> testfiles = new ArrayList<>();
        testfiles.add(new MockMultipartFile("test", new byte[0]));
        testfiles.add(new MockMultipartFile("test", new byte[0]));
        saunaService.addSaunaImages(sauna.getId(), testfiles);
        assertEquals(testfiles.size(), saunaService.getSaunaImages(sauna.getId()).size());
        List<SaunaImage> images = saunaService.getSaunaImages(sauna.getId());
        SaunaImage saunaImage = images.get(0);
        saunaService.removeSaunaImage(saunaImage.getId());
        assertEquals(1, saunaService.getSaunaImages(sauna.getId()).size());
    }

    /**
     * This test checks if the images can be found in the database.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getSaunaImage() throws IOException {
        assertThrows(IOException.class, () -> saunaService.getImage("not existing file"));
        assertThrows(NotFoundException.class, () -> saunaService.getSaunaImages(1));
        saunaService.addSauna(saunaTypeBody);
        Sauna sauna = saunaService.getAllSauna().get(0);
        assertTrue(saunaService.getSaunaImages(sauna.getId()).isEmpty());
        List<MultipartFile> testfiles = new ArrayList<>();
        testfiles.add(new MockMultipartFile("test1.txt", new byte[0]));
        testfiles.add(new MockMultipartFile("test2.txt", new byte[0]));
        saunaService.addSaunaImages(sauna.getId(), testfiles);
        List<SaunaImage> images = saunaService.getSaunaImages(sauna.getId());
        for (SaunaImage image : images) {
            assertDoesNotThrow(() -> saunaService.getImage(image.getFileName()));
        }
    }
}
