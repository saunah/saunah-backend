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

    @Column(name = "transportServiceDistance", nullable = false)
    private int transportServiceDistance;

    @Column(name = "transportServicePrice", nullable = false)
    private double transportServicePrice;

    @Column(name = "washServiceAmount", nullable = false)
    private int washServiceAmount;

    @Column(name = "washServicePrice", nullable = false)
    private double washServicePrice;

    @Column(name = "saunahImpAmount", nullable = false)
    private int saunahImpAmount;

    @Column(name = "saunahImpPrice", nullable = false)
    private double saunahImpPrice;

    @Column(name = "deposit", nullable = false)
    private boolean deposit;

    @Column(name = "depositPrice", nullable = false)
    private double depositPrice;

    @Column(name = "handTowelAmount", nullable = false)
    private int handTowelAmount;

    @Column(name = "handTowelPrice", nullable = false)
    private double handTowelPrice;

    @Column(name = "woodAmount", nullable = false)
    private int woodAmount;

    @Column(name = "woodPrice", nullable = false)
    private double woodPrice;

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

    public void setId(int id) {
        this.id = id;
    }

    public int getTransportServiceDistance() {
        return transportServiceDistance;
    }

    public void setTransportServiceDistance(int transportService) {
        this.transportServiceDistance = transportService;
    }

    public int getWashServiceAmount() {
        return washServiceAmount;
    }

    public void setWashServiceAmount(int washService) {
        this.washServiceAmount = washService;
    }

    public int getSaunahImpAmount() {
        return saunahImpAmount;
    }

    public void setSaunahImpAmount(int saunahImp) {
        this.saunahImpAmount = saunahImp;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public int getHandTowelAmount() {
        return handTowelAmount;
    }

    public void setHandTowelAmount(int handTowel) {
        this.handTowelAmount = handTowel;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public void setWoodAmount(int wood) {
        this.woodAmount = wood;
    }

    public double getTransportServicePrice() {
        return transportServicePrice;
    }

    public void setTransportServicePrice(double transportServicePrice) {
        this.transportServicePrice = transportServicePrice;
    }

    public double getWashServicePrice() {
        return washServicePrice;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public void setWashServicePrice(double washServicePrice) {
        this.washServicePrice = washServicePrice;
    }

    public double getSaunahImpPrice() {
        return saunahImpPrice;
    }

    public void setSaunahImpPrice(double saunahImpPrice) {
        this.saunahImpPrice = saunahImpPrice;
    }

    public double getHandTowelPrice() {
        return handTowelPrice;
    }

    public void setHandTowelPrice(double handTowelPrice) {
        this.handTowelPrice = handTowelPrice;
    }

    public double getWoodPrice() {
        return woodPrice;
    }

    public void setWoodPrice(double woodPrice) {
        this.woodPrice = woodPrice;
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
