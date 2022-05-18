package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.Price;

/**
 * This class is used as the response DTO object, when a price structure data was retrieved.
 */
public class PriceResponse {
    private int id;
    private float transportService;
    private float washService;
    private float saunahImp;
    private float deposit;
    private float handTowel;
    private float wood;
    private float hourlyRate;

    /**
     * This constructor sets all the fields of this object.
     */
    public PriceResponse(Price price) {
        this.id = price.getId();
        this.transportService = price.getTransportService();
        this.washService = price.getWashService();
        this.saunahImp = price.getSaunahImp();
        this.deposit = price.getDeposit();
        this.handTowel = price.getHandTowel();
        this.wood = price.getWood();
        this.hourlyRate = price.getHourlyRate();
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

    public float getHourlyRate() {
        return hourlyRate;
    }
}
