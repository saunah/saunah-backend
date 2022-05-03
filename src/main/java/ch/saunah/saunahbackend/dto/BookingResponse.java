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
    private boolean transportService;
    private boolean washService;
    private boolean saunahImp;
    private boolean deposit;
    private boolean handTowel;
    private boolean wood;

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
        this.saunaId = booking.getSaunaId();
        this.location = booking.getLocation();
        this.transportService = booking.isTransportService();
        this.washService = booking.isWashService();
        this.saunahImp = booking.isSaunahImp();
        this.deposit = booking.isDeposit();
        this.handTowel = booking.isHandTowel();
        this.wood = booking.isWood();
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
