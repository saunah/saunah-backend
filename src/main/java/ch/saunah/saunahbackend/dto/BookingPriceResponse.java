package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingPrice;

/**
 * This class is used as the response DTO object, when bookingPrice data was retrieved.
 */
public class BookingPriceResponse {

    private int id;
    private double transportServicePrice;
    private double washServicePrice;
    private double saunahImpPrice;
    private double depositPrice;
    private double handTowelPrice;
    private double woodPrice;
    private double discount;

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
        this.discount = bookingPrice.getDiscount();
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

    public double getDiscount() {
        return discount;
    }
}