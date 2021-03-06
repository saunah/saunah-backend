package ch.saunah.saunahbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Model of a bookingPrice structure
 */
@Entity(name = "bookingPrice")
public class BookingPrice {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "bookingPrice")
    private Booking booking;

    @Column(name = "transportServicePrice", nullable = false)
    private double transportServicePrice;

    @Column(name = "washServicePrice", nullable = false)
    private double washServicePrice;

    @Column(name = "saunahImpPrice", nullable = false)
    private double saunahImpPrice;

    @Column(name = "depositPrice", nullable = false)
    private double depositPrice;

    @Column(name = "handTowelPrice", nullable = false)
    private double handTowelPrice;

    @Column(name = "woodPrice", nullable = false)
    private double woodPrice;

    public int getId() {
        return id;
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

}
