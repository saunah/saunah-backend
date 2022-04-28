package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.controller.PriceController;
import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.repository.PriceRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests all Price service methods
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaunahBackendApplication.class)
public class PriceServiceTest {

    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceRepository priceRepository;
    private PriceBody priceBody = null;

    @Autowired
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        priceBody = new PriceBody();
        priceBody.setTransportService(1.50F);
        priceBody.setWashService(50.00F);
        priceBody.setSaunahImp(25.00F);
        priceBody.setDeposit(100F);
        priceBody.setHandTowel(5.00F);
        priceBody.setWood(20.00F);
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * This test checks if the user can create a new Price struct with the correct value for the fields
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewPrice() {
        Price price = priceService.addPrice(priceBody);
        Iterable<Price> prices = priceRepository.findAll();
        assertTrue(prices.iterator().hasNext());
        assertThrows(NullPointerException.class, () -> priceService.addPrice(null));
        Price price2 = priceService.addPrice(priceBody);
        assertNotEquals(price.getId(), price2.getId());

        checkPriceFields(priceBody, price);
    }

    /**
     * This test checks if the user can get a specific Price struct
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getPrice() {
        assertThrows(NotFoundException.class, () -> priceService.getPrice(1));
        priceService.addPrice(priceBody);
        assertNotNull(priceService.getPrice(1));
    }

    /**
     * This test checks if the user can edit a Price structure
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editPrice() {
        Price price = priceService.addPrice(priceBody);
        PriceBody priceBodyChanged = new PriceBody();
        priceBodyChanged.setDeposit(1000);
        priceBodyChanged.setTransportService(1000);
        priceBodyChanged.setWashService(1000);
        priceBodyChanged.setWood(1000);
        priceBodyChanged.setHandTowel(1000);
        priceBodyChanged.setSaunahImp(1000);
        price = priceService.editPrice(price.getId(), priceBodyChanged);
        checkPriceFields(priceBodyChanged, price);
    }

    /**
     * This test checks if the user can delete a Price Structure
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removePrice() {
        priceService.addPrice(priceBody);
        Iterable<Price> prices = priceRepository.findAll();
        assertTrue(prices.iterator().hasNext());
        priceService.removePrice(1);
        prices = priceRepository.findAll();
        assertFalse(prices.iterator().hasNext());
    }

    /**
     * This method helps checking if the values are correct
     * @param priceBody the Parameters of a price structure
     * @param price the instance of a Price
     */
    private void checkPriceFields(PriceBody priceBody, Price price) {
        assertEquals(priceBody.getDeposit(), price.getDeposit());
        assertEquals(priceBody.getWashService(), price.getWashService());
        assertEquals(priceBody.getTransportService(), price.getTransportService());
        assertEquals(priceBody.getHandTowel(), price.getHandTowel());
        assertEquals(priceBody.getSaunahImp(), price.getSaunahImp());
        assertEquals(priceBody.getWood(), price.getWood());
    }
}
