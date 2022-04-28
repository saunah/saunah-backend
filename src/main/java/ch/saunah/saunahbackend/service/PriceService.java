package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.dto.SaunaTypeBody;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.repository.PriceRepository;
import ch.saunah.saunahbackend.repository.SaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Objects;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Price addPrice(PriceBody priceBody) throws NullPointerException {
        Objects.requireNonNull(priceBody, "PriceBody must not be null!");
        Objects.requireNonNull(priceBody.getDeposit(), "Deposit must not be null!");
        Objects.requireNonNull(priceBody.getWashService(), "WashService must not be null!");
        Objects.requireNonNull(priceBody.getTransportService(), "TransportService must not be null!");
        Objects.requireNonNull(priceBody.getHandTowel(), "HandTowel must not be null!");
        Objects.requireNonNull(priceBody.getWood(), "Wood must not be null!");
        Objects.requireNonNull(priceBody.getSaunahImp(), "SauNah Imp must not be null!");

        Price price = new Price();
        setPriceFields(price, priceBody);

        return priceRepository.save(price);
    }

    public Price editPrice(int id, PriceBody priceBody) throws NullPointerException {
        return null;
    }

    public String removePrice(int id) throws NullPointerException {
        return null;
    }

    public Price getPrice(int id) throws NotFoundException {
        Price price = priceRepository.findById(id).orElse(null);
        if (price == null){
            throw new NotFoundException(String.format("Price format with id %d not found!", id));
        }
        return price;
    }

    private Price setPriceFields (Price price, PriceBody priceBody) {
        price.setDeposit(priceBody.getDeposit());
        price.setWashService(priceBody.getWashService());
        price.setTransportService(priceBody.getTransportService());
        price.setHandTowel(priceBody.getHandTowel());
        price.setWood(priceBody.getWood());
        price.setSaunahImp(priceBody.getSaunahImp());
        return price;
    }


}
