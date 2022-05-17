package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingSauna;

/**
 * This class is used as the response DTO object, when bookingSauna data was retrieved.
 */
public class BookingSaunaResponse {

    private int id;
    private int saunaId;
    private String saunaName;
    private String saunaDescription;
    private boolean saunaIsMobile;
    private int saunaPrice;
    private int saunaMaxTemp;
    private int saunaNumberOfPeople;
    private String saunaLocation;
    private String saunaStreet;
    private int saunaZip;
    private String saunaType;

    /**
     * This constructor sets all the fields of this object.
     */
    public BookingSaunaResponse(BookingSauna bookingSauna) {
        this.id = bookingSauna.getId();
        this.saunaId = bookingSauna.getSaunaId();
        this.saunaName = bookingSauna.getSaunaName();
        this.saunaDescription = bookingSauna.getSaunaDescription();
        this.saunaIsMobile = bookingSauna.isSaunaIsMobile();
        this.saunaPrice = bookingSauna.getSaunaPrice();
        this.saunaMaxTemp = bookingSauna.getSaunaMaxTemp();
        this.saunaNumberOfPeople = bookingSauna.getSaunaNumberOfPeople();
        this.saunaLocation = bookingSauna.getSaunaLocation();
        this.saunaStreet = bookingSauna.getSaunaStreet();
        this.saunaZip = bookingSauna.getSaunaZip();
        this.saunaType = bookingSauna.getSaunaType();
    }

    public int getId() {
        return id;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public String getSaunaName() {
        return saunaName;
    }

    public String getSaunaDescription() {
        return saunaDescription;
    }

    public boolean isSaunaIsMobile() {
        return saunaIsMobile;
    }

    public int getSaunaPrice() {
        return saunaPrice;
    }

    public int getSaunaMaxTemp() {
        return saunaMaxTemp;
    }

    public int getSaunaNumberOfPeople() {
        return saunaNumberOfPeople;
    }

    public String getSaunaLocation() {
        return saunaLocation;
    }

    public String getSaunaStreet() {
        return saunaStreet;
    }

    public int getSaunaZip() {
        return saunaZip;
    }

    public String getSaunaType() {
        return saunaType;
    }
}
