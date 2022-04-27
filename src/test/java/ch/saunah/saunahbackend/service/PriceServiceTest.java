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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewPrice() {
        Price price = priceService.addPrice(priceBody);
        Iterable<Price> prices = priceRepository.findAll();
        assertTrue(prices.iterator().hasNext());
        assertThrows(NullPointerException.class, () -> priceService.addPrice(null));
        assertThrows(NullPointerException.class, () -> priceService.addPrice(new PriceBody()));
        Price price2 = priceService.addPrice(priceBody);
        assertNotEquals(price.getId(), price2.getId());

        assertEquals(priceBody.getDeposit(), price.getDeposit());
        assertEquals(priceBody.getWashService(), price.getWashService());
        assertEquals(priceBody.getTransportService(), price.getTransportService());
        assertEquals(priceBody.getHandTowel(), price.getHandTowel());
        assertEquals(priceBody.getSaunahImp(), price.getSaunahImp());
        assertEquals(priceBody.getWood(), price.getWood());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void getPrice() {

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void editPrice() {

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removePrice() {

    }


}
