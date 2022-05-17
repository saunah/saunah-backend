package ch.saunah.saunahbackend.model;

import javax.persistence.*;

/**
 * Model of a price structure
 */
@Entity(name = "price")
public class Price {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "transportService", nullable = false)
    private float transportService;

    @Column(name = "washService", nullable = false)
    private float washService;

    @Column(name = "saunahImp", nullable = false)
    private float saunahImp;

    @Column(name = "deposit", nullable = false)
    private float deposit;

    @Column(name = "handTowel", nullable = false)
    private float handTowel;

    @Column(name = "wood", nullable = false)
    private float wood;

    @Column(name = "discount", nullable = false)
    private float discount;

    @Column(name = "discountDescription", nullable = false)
    private String discountDescription;


    public Price() {

    }

    public int getId() {
        return id;
    }

    public float getTransportService() {
        return transportService;
    }

    public float getWashService() {
        return washService;
    }

    public float getSaunahImp() {
        return saunahImp;
    }

    public float getDeposit() {
        return deposit;
    }

    public float getHandTowel() {
        return handTowel;
    }

    public float getWood() {
        return wood;
    }

    public float getDiscount() {
        return discount;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setTransportService(float transportService) {
        this.transportService = transportService;
    }

    public void setWashService(float washService) {
        this.washService = washService;
    }

    public void setSaunahImp(float saunahImp) {
        this.saunahImp = saunahImp;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public void setHandTowel(float handTowel) {
        this.handTowel = handTowel;
    }

    public void setWood(float wood) {
        this.wood = wood;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }
}
