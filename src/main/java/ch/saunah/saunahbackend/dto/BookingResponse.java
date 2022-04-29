package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.SaunaBooking;

import java.util.Date;

/**
 * This class is used as the response DTO object, when booking data was retrieved.
 */
public class BookingResponse {

    private String saunaName;
    private Date bookingDate;
    private BookingState state;
    private int userID;
    private int saunaId;
    private String location;
    private boolean transportService;
    private boolean washService;
    private boolean saunahImp;
    private boolean deposit;
    private boolean handTowel;
    private boolean wood;

    public BookingResponse(SaunaBooking saunaBooking) {
        this.saunaName = saunaBooking.getSaunaName();
        this.bookingDate = saunaBooking.getBookingDate();
        this.state = saunaBooking.getState();
        this.userID = saunaBooking.getUserId();
        this.saunaId = saunaBooking.getSaunaId();
        this.location = saunaBooking.getLocation();
        this.transportService = saunaBooking.isTransportService();
        this.washService = saunaBooking.isWashService();
        this.saunahImp = saunaBooking.isSaunahImp();
        this.deposit = saunaBooking.isDeposit();
        this.handTowel = saunaBooking.isHandTowel();
        this.wood = saunaBooking.isWood();
    }

    public String getSaunaName() {
        return saunaName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public BookingState getState() {
        return state;
    }

    public int getUserID() {
        return userID;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isTransportService() {
        return transportService;
    }

    public boolean isWashService() {
        return washService;
    }

    public boolean isSaunahImp() {
        return saunahImp;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public boolean isHandTowel() {
        return handTowel;
    }

    public boolean isWood() {
        return wood;
    }
}
