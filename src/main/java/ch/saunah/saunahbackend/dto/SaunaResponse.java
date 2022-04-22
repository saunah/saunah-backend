package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.Sauna;
import ch.saunah.saunahbackend.model.SaunaType;

/**
 * This class is used as the response DTO object, when sauna data was retrieved.
 */
public class SaunaResponse {
    private int id;
    private String name;
    private String description;
    private boolean isMobile;
    private int price;
    private int maxTemp;
    private int numberOfPeople;
    private String location;
    private String street;
    private int zip;
    private SaunaType type;

    /**
     * This constructor sets all the fields of this object.
     */
    public SaunaResponse(Sauna sauna) {
        this.id = sauna.getId();
        this.name = sauna.getName();
        this.description = sauna.getDescription();
        this.isMobile = sauna.isMobile();
        this.price = sauna.getPrice();
        this.maxTemp = sauna.getMaxTemp();
        this.numberOfPeople = sauna.getNumberOfPeople();
        this.location = sauna.getLocation();
        this.street = sauna.getStreet();
        this.zip = sauna.getZip();
        this.type = sauna.getType();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxTemp() { return maxTemp; }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getLocation() {
        return location;
    }

    public String getStreet() {
        return street;
    }

    public int getZip() {
        return zip;
    }

    public SaunaType getType() {
        return type;
    }
}
