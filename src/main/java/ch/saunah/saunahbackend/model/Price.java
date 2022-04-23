package ch.saunah.saunahbackend.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Price {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "baseRent", nullable = false)
    private float baseRent;

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

    @Column(name = "extras", nullable = false)
    private float extras;


    public Price(){

    }

    public float getBaseRent() { return baseRent; }

    public float getTransportService() { return  transportService; }

    public float getWashService() { return  washService; }

    public float getSaunahImp() { return saunahImp; }

    public float getDeposit() { return deposit; }

    public float getHandTowel() { return handTowel; }

    public float getWood() { return wood; }

    public float getExtras() { return extras; }

    public void setBaseRent(float baseRent) { this.baseRent = baseRent; }

    public void setTransportService(float transportService) { this.transportService = transportService; }

    public void setWashService(float washService) { this.washService = washService; }

    public void setSaunahImp(float saunahImp) { this.saunahImp = saunahImp; }

    public void setDeposit(float deposit) { this.deposit = deposit; }

    public void setHandTowel(float handTowel) { this.handTowel = handTowel; }

    public void setWood(float wood) { this.wood = wood; }

    public void setExtras(float extras) { this.extras = extras;}


}
