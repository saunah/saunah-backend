package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.Sauna;

public class SaunaResponse {
    private int id;
    private String name;
    private String description;
    private boolean isMobile;
    private int prize;
    private int maxTemp;
    private int numberOfPeople;
    private String location;
    private String street;
    private int plz;

    public SaunaResponse(Sauna sauna) {
        this.id = sauna.getId();
        this.name = sauna.getName();
        this.description = sauna.getDescription();
        this.isMobile = sauna.getType();
        this.prize = sauna.getPrize();
        this.maxTemp = sauna.getMaxTemp();
        this.numberOfPeople = sauna.getNumberOfPeople();
        this.location = sauna.getLocation();
        this.street = sauna.getStreet();
        this.plz = sauna.getPlz();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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


}
