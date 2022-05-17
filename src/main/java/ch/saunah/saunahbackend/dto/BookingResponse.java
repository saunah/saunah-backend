package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.Booking;

import java.util.Date;

/**
 * This class is used as the response DTO object, when booking data was retrieved.
 */
public class BookingResponse {

    private int id;
    private int bookingPriceId;
    private int bookingSaunaId;
    private Date startBookingDate;
    private Date endBookingDate;
    private Date creation;
    private BookingState state;
    private int userId;
    private String location;
    private int transportServiceDistance;
    private boolean washService;
    private int saunahImpAmount;
    private boolean deposit;
    private int handTowelAmount;
    private int woodAmount;
    private String discountDescription;
    private String comment;
    private double endPrice;

    /**
     * This constructor sets all the fields of this object.
     */
    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.bookingPriceId = booking.getBookingPrice().getId();
        this.bookingSaunaId = booking.getBookingSauna().getId();
        this.startBookingDate = booking.getStartBookingDate();
        this.endBookingDate = booking.getEndBookingDate();
        this.creation = booking.getCreation();
        this.state = booking.getState();
        this.userId = booking.getUserId();
        this.location = booking.getLocation();
        this.transportServiceDistance = booking.getTransportServiceDistance();
        this.washService = booking.isWashService();
        this.saunahImpAmount = booking.getSaunahImpAmount();
        this.deposit = booking.isDeposit();
        this.handTowelAmount = booking.getHandTowelAmount();
        this.woodAmount = booking.getWoodAmount();
        this.discountDescription = booking.getDiscountDescription();
        this.comment = booking.getComment();
        this.endPrice = booking.getEndPrice();
    }

    public int getId() {
        return id;
    }

    public int getBookingPriceId() {
        return bookingPriceId;
    }

    public int getBookingSaunaId() {
        return bookingSaunaId;
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

    public int getUserId() {
        return userId;
    }

    public String getLocation() {
        return location;
    }

    public int getTransportServiceDistance() {
        return transportServiceDistance;
    }

    public boolean isWashService() {
        return washService;
    }

    public int getSaunahImpAmount() {
        return saunahImpAmount;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public int getHandTowelAmount() {
        return handTowelAmount;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public String getComment() {
        return comment;
    }

    public double getEndPrice() {
        return endPrice;
    }

}
