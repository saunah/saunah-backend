package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.Booking;

import java.util.Date;

/**
 * This class is used as the response DTO object, when booking data was retrieved.
 */
public class BookingResponse {

    private int id;
    private Date startBookingDate;
    private Date endBookingDate;
    private Date creation;
    private BookingState state;
    private double endPrice;
    private int userId;
    private int saunaId;
    private String location;
    private int transportServiceDistance;
    private double transportServicePrice;
    private int washServiceAmount;
    private double washServicePrice;
    private int saunahImpAmount;
    private double saunahImpPrice;
    private boolean deposit;
    private double depositPrice;
    private int handTowelAmount;
    private double handTowelPrice;
    private int woodAmount;
    private double woodPrice;
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
    private String googleEventID;

    /**
     * This constructor sets all the fields of this object.
     */
    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.startBookingDate = booking.getStartBookingDate();
        this.endBookingDate = booking.getEndBookingDate();
        this.creation = booking.getCreation();
        this.state = booking.getState();
        this.endPrice = booking.getEndPrice();
        this.userId = booking.getUserId();
        this.location = booking.getLocation();
        this.transportServiceDistance = booking.getTransportServiceDistance();
        this.transportServicePrice = booking.getTransportServicePrice();
        this.washServiceAmount = booking.getWashServiceAmount();
        this.washServicePrice = booking.getWashServicePrice();
        this.saunahImpAmount = booking.getSaunahImpAmount();
        this.saunahImpPrice = booking.getSaunahImpPrice();
        this.deposit = booking.isDeposit();
        this.depositPrice = booking.getDepositPrice();
        this.handTowelAmount = booking.getHandTowelAmount();
        this.handTowelPrice = booking.getHandTowelPrice();
        this.woodAmount = booking.getWoodAmount();
        this.woodPrice = booking.getWoodPrice();
        this.saunaId = booking.getSaunaId();
        this.saunaName = booking.getSaunaName();
        this.saunaDescription = booking.getSaunaDescription();
        this.saunaIsMobile = booking.isSaunaIsMobile();
        this.saunaPrice = booking.getSaunaId();
        this.saunaMaxTemp = booking.getSaunaId();
        this.saunaNumberOfPeople = booking.getSaunaId();
        this.saunaLocation = booking.getSaunaLocation();
        this.saunaStreet = booking.getSaunaStreet();
        this.saunaZip = booking.getSaunaZip();
        this.saunaType = booking.getSaunaType();
        this.googleEventID = booking.getGoogleEventID();
    }

    public int getId() {
        return id;
    }

    public Date getStartBookingDate() {
        return startBookingDate;
    }

    public Date getEndBookingDate() {
        return endBookingDate;
    }

    public Date getCreation() {
        return creation;
    }

    public BookingState getState() {
        return state;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public int getUserId() {
        return userId;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public String getLocation() {
        return location;
    }

    public int getTransportServiceDistance() {
        return transportServiceDistance;
    }

    public int getWashServiceAmount() {
        return washServiceAmount;
    }

    public int getSaunahImpAmount() {
        return saunahImpAmount;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public int getHandTowelAmount() {
        return handTowelAmount;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public String getSaunaName() {
        return saunaName;
    }

    public double getTransportServicePrice() {
        return transportServicePrice;
    }

    public double getWashServicePrice() {
        return washServicePrice;
    }

    public double getSaunahImpPrice() {
        return saunahImpPrice;
    }

    public double getHandTowelPrice() {
        return handTowelPrice;
    }

    public double getWoodPrice() {
        return woodPrice;
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

    public String getGoogleEventID() {
        return googleEventID;
    }
}
