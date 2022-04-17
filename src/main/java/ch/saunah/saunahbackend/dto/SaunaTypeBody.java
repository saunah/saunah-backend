package ch.saunah.saunahbackend.dto;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class is used as the DTO object when creating a Sauna.
 */
public class SaunaTypeBody {
    private long id;
    private String name;
    private String description;
    private boolean picture;
    private boolean isMobile;
    private int prize;
    private int maxTemp;
    private int numberOfPeople;
    private String location;
    private String street;
    private int plz;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getPicture() {
        return picture;
    }

    public boolean getType() {
        return isMobile;
    }

    public int getPrize() {
        return prize;
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

    public int getPlz() {
        return plz;
    }

    public void setId(long id) { this.id = id;}

    public void setName(String name) { this.name = name;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }

    public void setType(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }


}