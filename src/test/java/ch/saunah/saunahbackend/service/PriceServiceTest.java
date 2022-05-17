package ch.saunah.saunahbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.repository.PriceRepository;

/**
 * This class tests all Price service methods
 */
@SpringBootTest(classes = SaunahBackendApplication.class)
class PriceServiceTest {

    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceRepository priceRepository;
    private PriceBody priceBody = null;

    @BeforeEach
    void setUp() {
        priceBody = new PriceBody();
        priceBody.setTransportService(1.50F);
        priceBody.setWashService(50.00F);
        priceBody.setSaunahImp(25.00F);
        priceBody.setDeposit(100F);
        priceBody.setHandTowel(5.00F);
        priceBody.setWood(20.00F);
        priceBody.setDiscount(-20.00F);
        priceBody.setDiscountDescription(":)");
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
     * This test checks if all saunas can be found that exist in the database
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getAllPrice() {
        priceService.addPrice(priceBody);
        priceService.addPrice(priceBody);
        priceService.addPrice(priceBody);
        assertEquals(3, priceRepository.count());
        priceService.removePrice(1);
        assertEquals(2, priceRepository.count());
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
        priceBodyChanged.setDiscount(-30);
        priceBodyChanged.setDiscountDescription(":D");
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
     *
     * @param priceBody the Parameters of a price structure
     * @param price     the instance of a Price
     */
    private void checkPriceFields(PriceBody priceBody, Price price) {
        assertEquals(priceBody.getDeposit(), price.getDeposit());
        assertEquals(priceBody.getWashService(), price.getWashService());
        assertEquals(priceBody.getTransportService(), price.getTransportService());
        assertEquals(priceBody.getHandTowel(), price.getHandTowel());
        assertEquals(priceBody.getSaunahImp(), price.getSaunahImp());
        assertEquals(priceBody.getWood(), price.getWood());
        assertEquals(priceBody.getDiscount(), price.getDiscount());
        assertEquals(priceBody.getDiscountDescription(), price.getDiscountDescription());
    }
}
