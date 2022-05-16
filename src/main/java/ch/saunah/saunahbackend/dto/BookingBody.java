package ch.saunah.saunahbackend.dto;

import java.util.Date;

/**
 * This class is used as the DTO object when creating a Booking.
 */
public class BookingBody {
    private Date startBookingDate;
    private Date endBookingDate;
    private int saunaId;
    private String location;
    private int transportServiceDistance;
    private int washServiceAmount;
    private int saunahImpAmount;
    private int handTowelAmount;
    private int woodAmount;
    private String discountDescription;
    private String comment;

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

    public int getTransportServiceDistance() {
        return transportServiceDistance;
    }

    public void setTransportServiceDistance(int transportServiceDistance) {
        this.transportServiceDistance = transportServiceDistance;
    }

    public int getWashServiceAmount() {
        return washServiceAmount;
    }

    public void setWashServiceAmount(int washServiceAmount) {
        this.washServiceAmount = washServiceAmount;
    }

    public int getSaunahImpAmount() {
        return saunahImpAmount;
    }

    public void setSaunahImpAmount(int saunahImpAmount) {
        this.saunahImpAmount = saunahImpAmount;
    }

    public int getHandTowelAmount() {
        return handTowelAmount;
    }

    public void setHandTowelAmount(int handTowelAmount) {
        this.handTowelAmount = handTowelAmount;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public void setWoodAmount(int woodAmount) {
        this.woodAmount = woodAmount;
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
}
