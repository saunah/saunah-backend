package ch.saunah.saunahbackend.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model of a bookingSauna structure
 */
@Entity(name = "bookingSauna")
public class BookingSauna implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "bookingSauna")
    private Booking booking;

    @Column(name = "saunaId", nullable = false)
    private int saunaId;

    @Column(name = "sauna_name", nullable = false)
    private String saunaName;

    @Column(name = "sauna_description")
    private String saunaDescription;

    @Column(name = "sauna_isMobile", nullable = false)
    private boolean saunaIsMobile;

    @Column(name = "sauna_price", nullable = false)
    private int saunaPrice;

    @Column(name = "sauna_max_temp", nullable = false)
    private int saunaMaxTemp;

    @Column(name = "sauna_numberOfPeople", nullable = false)
    private int saunaNumberOfPeople;

    @Column(name = "sauna_location", nullable = false)
    private String saunaLocation;

    @Column(name = "sauna_street", nullable = false)
    private String saunaStreet;

    @Column(name = "sauna_zip", nullable = false)
    private int saunaZip;

    @Column(name = "sauna_type", nullable = false)
    private String saunaType;

    public BookingSauna() {

    }

    public int getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getSaunaName() {
        return saunaName;
    }

    public void setSaunaName(String saunaName) {
        this.saunaName = saunaName;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public void setSaunaId(int saunaId) {
        this.saunaId = saunaId;
    }

    public String getSaunaDescription() {
        return saunaDescription;
    }

    public void setSaunaDescription(String saunaDescription) {
        this.saunaDescription = saunaDescription;
    }

    public boolean isSaunaIsMobile() {
        return saunaIsMobile;
    }

    public void setSaunaIsMobile(boolean saunaIsMobile) {
        this.saunaIsMobile = saunaIsMobile;
    }

    public int getSaunaPrice() {
        return saunaPrice;
    }

    public void setSaunaPrice(int saunaPrice) {
        this.saunaPrice = saunaPrice;
    }

    public int getSaunaMaxTemp() {
        return saunaMaxTemp;
    }

    public void setSaunaMaxTemp(int saunaMaxTemp) {
        this.saunaMaxTemp = saunaMaxTemp;
    }

    public int getSaunaNumberOfPeople() {
        return saunaNumberOfPeople;
    }

    public void setSaunaNumberOfPeople(int saunaNumberOfPeople) {
        this.saunaNumberOfPeople = saunaNumberOfPeople;
    }

    public String getSaunaLocation() {
        return saunaLocation;
    }

    public void setSaunaLocation(String saunaLocation) {
        this.saunaLocation = saunaLocation;
    }

    public String getSaunaStreet() {
        return saunaStreet;
    }

    public void setSaunaStreet(String saunaStreet) {
        this.saunaStreet = saunaStreet;
    }

    public int getSaunaZip() {
        return saunaZip;
    }

    public void setSaunaZip(int saunaZip) {
        this.saunaZip = saunaZip;
    }

    public String getSaunaType() {
        return saunaType;
    }

    public void setSaunaType(String saunaType) {
        this.saunaType = saunaType;
    }
}
