package ch.saunah.saunahbackend.service;

import ch.saunah.saunahbackend.dto.PriceBody;
import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Objects;

/**
 * This class contains add, edit, get and remove methods for price structures
 */
@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    /**
     * Adds a new Price structure to the Database
     * @param priceBody the parameter needed to create a Price structure
     * @return a new Price structure
     * @throws NullPointerException if a required Object is null
     */
    public Price addPrice(PriceBody priceBody) throws NullPointerException {
        Objects.requireNonNull(priceBody, "PriceBody must not be null!");
        Price price = new Price();
        setPriceFields(price, priceBody);

        return priceRepository.save(price);
    }

    /**
     * Lets the user edit an existing price structure
     * @param id The id of the Price structure that shall be edited
     * @param priceBody the parameters of the price structure that is being edited
     * @return The price structure with the changed values
     * @throws NullPointerException
     */
    public Price editPrice(int id, PriceBody priceBody) throws NullPointerException {
        Price editPrice = getPrice(id);
        setPriceFields(editPrice, priceBody);
        return priceRepository.save(editPrice);
    }

    /**
     * Remove an existing price structure from the database
     * @param id the id of the price structure that should be deleted
     * @return message if price structure has been successfully been deleted
     * @throws NullPointerException
     */
    public String removePrice(int id) throws NullPointerException {
        priceRepository.deleteById(id);
        return String.format("The Price Structure with id %s has been removed", id);
    }

    /**
     * Get a specific price structure from the database
     * @param id The id of the price structure that is requested
     * @return the requested price structure
     * @throws NotFoundException if no such price structure exists
     */
    public Price getPrice(int id) throws NotFoundException {
        Price price = priceRepository.findById(id).orElse(null);
        if (price == null){
            throw new NotFoundException(String.format("Price format with id %d not found!", id));
        }
        return price;
    }

    /**
     * Method that helps to set values for price structure fields
     * @param price the price instance whose fields need to be set
     * @param priceBody the parameters to be set in the corresponding fields
     * @return the price instance with the set field values
     */
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
