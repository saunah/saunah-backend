package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;

public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Price addPrice(PriceBody priceBody) throws NullPointerException {
        return null;
    }

    public Price editPrice(int id, PriceBody priceBody) throws NullPointerException {
        return null;
    }

    public String removePrice(int id) throws NullPointerException {
        return null;
    }

    public Price getPrice(int id) throws NotFoundException {
        return null;
    }

    private Sauna setPriceFields (Price price, PriceBody priceBody) {
        return null;
    }


}
