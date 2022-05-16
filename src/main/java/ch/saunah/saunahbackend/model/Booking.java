package ch.saunah.saunahbackend.model;

import java.util.Date;

import javax.persistence.*;

/**
 * Model of a booking structure
 */
@Entity(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingPriceId", referencedColumnName = "id")
    private BookingPrice bookingPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookingSaunaId", referencedColumnName = "id")
    private BookingSauna bookingSauna;

    @Column(name = "start_booking_date", nullable = false)
    private Date startBookingDate;

    @Column(name = "end_booking_date", nullable = false)
    private Date endBookingDate;

    @Column(name = "creation", nullable = false)
    private Date creation;

    @Column(name = "state", nullable = false)
    private BookingState state;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "transportServiceDistance", nullable = false)
    private int transportServiceDistance;

    @Column(name = "washService", nullable = false)
    private boolean washService;

    @Column(name = "saunahImpAmount", nullable = false)
    private int saunahImpAmount;

    @Column(name = "deposit", nullable = false)
    private boolean deposit;

    @Column(name = "handTowelAmount", nullable = false)
    private int handTowelAmount;

    @Column(name = "woodAmount", nullable = false)
    private int woodAmount;

    @Column(name = "discountDescription", nullable = false)
    private String discountDescription;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "endPrice", nullable = false)
    private double endPrice;

    @Column(name = "google_event_id", nullable = true)
    private String googleEventID;

    public Booking() {

    }

    public int getId() {
        return id;
    }

    public BookingPrice getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(BookingPrice bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public BookingSauna getBookingSauna() {
        return bookingSauna;
    }

    public void setBookingSauna(BookingSauna bookingSauna) {
        this.bookingSauna = bookingSauna;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTransportServiceDistance() {
        return transportServiceDistance;
    }

    public void setTransportServiceDistance(int transportService) {
        this.transportServiceDistance = transportService;
    }

    public boolean isWashService() {
        return washService;
    }

    public void setWashService(boolean washService) {
        this.washService = washService;
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

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public void setGoogleEventID(String googleEventID) {
        this.googleEventID = googleEventID;
    }

    public String getGoogleEventID() {
        return googleEventID;
    }
}
