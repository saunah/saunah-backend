package ch.saunah.saunahbackend.dto;

/**
 * This class is used as the DTO object when creating a Sauna.
 */
public class SaunaTypeBody {
    private String name;
    private String description;
    private boolean isMobile;
    private float price;
    private int maxTemp;
    private int numberOfPeople;
    private String location;
    private String street;
    private int zip;
    private String type;
    private String googleCalendarId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public float getPrice() {
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

    public String getType() {
        return type;
    }

    public String getGoogleCalendarId() {
        return googleCalendarId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGoogleCalendarId(String googleCalendarId) {
        this.googleCalendarId = googleCalendarId;
    }
}
