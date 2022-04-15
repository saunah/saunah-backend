package ch.saunah.saunahbackend;

import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaRepository;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SaunaControllerTests {

    @Autowired
    private SaunaRepository saunaRepository;
    @Autowired
    private SaunaController saunaController;

    @Test
    void addNewSauna() {
        Iterable<Sauna> saunas = saunaRepository.findAll();
        assertFalse(saunas.iterator().hasNext());


        boolean saunaCreated = saunaController.createSauna("MobileSauna", "a New type of Sauna for relaxing", true,
            true, 150, 51, 4,"Zürich", "Bahnhofstrasse 4", 9000);
        saunas = saunaRepository.findAll();
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
