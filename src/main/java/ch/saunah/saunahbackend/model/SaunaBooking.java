package ch.saunah.saunahbackend.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Model of a sauna booking
 */
@Entity(name = "sauna_booking")
public class SaunaBooking {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sauna_name", nullable = false)
    private String saunaName;

    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "state", nullable = false)
    private BookingState state;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "saunaId", nullable = false)
    private int saunaId;

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

    public SaunaBooking(){

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
}
