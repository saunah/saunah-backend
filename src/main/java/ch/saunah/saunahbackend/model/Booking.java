package ch.saunah.saunahbackend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model of a booking structure
 */
@Entity(name = "booking")
public class Booking {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_booking_date", nullable = false)
    private Date startBookingDate;

    @Column(name = "end_booking_date", nullable = false)
    private Date endBookingDate;

    @Column(name = "creation", nullable = false)
    private Date creation;

    @Column(name = "state", nullable = false)
    private BookingState state;

    @Column(name = "endPrice", nullable = false)
    private double endPrice;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "transportService", nullable = false)
    private boolean transportService;

    @Column(name = "washService", nullable = false)
    private boolean washService;

    @Column(name = "saunahImp", nullable = false)
    private boolean saunahImp;

    @Column(name = "deposit", nullable = false)
    private boolean deposit;

    @Column(name = "handTowel", nullable = false)
    private boolean handTowel;

    @Column(name = "wood", nullable = false)
    private boolean wood;

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

    @Column(name = "google_event_id", nullable = true)
    private String googleEventID;

    public Booking() {

    }

    public int getId() {
        return id;
    }

    public String getSaunaName() {
        return saunaName;
    }

    public void setSaunaName(String saunaName) {
        this.saunaName = saunaName;
    }

    public Date getStartBookingDate() {
        return startBookingDate;
    }

    public void setStartBookingDate(Date startBookingDate) {
        this.startBookingDate = startBookingDate;
    }

    public Date getEndBookingDate() {
        return endBookingDate;
    }

    public void setEndBookingDate(Date endBookingDate) {
        this.endBookingDate = endBookingDate;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public void setSaunaId(int saunaId) {
        this.saunaId = saunaId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isTransportService() {
        return transportService;
    }

    public void setTransportService(boolean transportService) {
        this.transportService = transportService;
    }

    public boolean isWashService() {
        return washService;
    }

    public void setWashService(boolean washService) {
        this.washService = washService;
    }

    public boolean isSaunahImp() {
        return saunahImp;
    }

    public void setSaunahImp(boolean saunahImp) {
        this.saunahImp = saunahImp;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public boolean isHandTowel() {
        return handTowel;
    }

    public void setHandTowel(boolean handTowel) {
        this.handTowel = handTowel;
    }

    public boolean isWood() {
        return wood;
    }

    public void setWood(boolean wood) {
        this.wood = wood;
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

    public void setGoogleEventID(String googleEventID) {
        this.googleEventID = googleEventID;
    }

    public String getGoogleEventID() {
        return googleEventID;
    }
}
