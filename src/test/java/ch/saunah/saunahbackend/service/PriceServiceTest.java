package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.SaunahBackendApplication;
import ch.saunah.saunahbackend.controller.PriceController;
import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.repository.PriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

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
        priceBody.setBaseRent(108.00F);
        priceBody.setTransportService(1.50F);
        priceBody.setWashService(50.00F);
        priceBody.setSaunahImp(25.00F);
        priceBody.setDeposit(100F);
        priceBody.setHandTowel(5.00F);
        priceBody.setWood(20.00F);
        priceBody.setExtras(50.00F);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNewPrice() {

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