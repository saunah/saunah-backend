package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.BookingState;

import java.util.Date;

/**
 * This class is used as the DTO object when creating a Booking.
 */
public class BookingBody {
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

    public String getSaunaName() {
        return saunaName;
    }

    public void setSaunaName(String saunaName) {
        this.saunaName = saunaName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = saunaId;
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
}
