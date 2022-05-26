package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.BookingPrice;

/**
 * This class is used as the response DTO object, when bookingPrice data was retrieved.
 */
public class BookingPriceResponse {

    private final int id;
    private final double transportServicePrice;
    private final double washServicePrice;
    private final double saunahImpPrice;
    private final double depositPrice;
    private final double handTowelPrice;
    private final double woodPrice;

    /**
     * This constructor sets all the fields of this object.
     */
    public BookingPriceResponse(BookingPrice bookingPrice) {
        this.id = bookingPrice.getId();
        this.transportServicePrice = bookingPrice.getTransportServicePrice();
        this.washServicePrice = bookingPrice.getWashServicePrice();
        this.saunahImpPrice = bookingPrice.getSaunahImpPrice();
        this.depositPrice = bookingPrice.getDepositPrice();
        this.handTowelPrice = bookingPrice.getHandTowelPrice();
        this.woodPrice = bookingPrice.getWoodPrice();
    }

    public int getId() {
        return id;
    }

    public double getTransportServicePrice() {
        return transportServicePrice;
    }

    public double getWashServicePrice() {
        return washServicePrice;
    }

    public double getSaunahImpPrice() {
        return saunahImpPrice;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public double getHandTowelPrice() {
        return handTowelPrice;
    }

    public double getWoodPrice() {
        return woodPrice;
    }

}
