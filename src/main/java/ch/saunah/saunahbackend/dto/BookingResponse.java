package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.SaunaBooking;

/**
 * This class is used as the response DTO object, when booking data was retrieved.
 */
public class BookingResponse {

    private int userID;
    private int saunaId;
    private String location;


    public BookingResponse(SaunaBooking saunaBooking) {
    }
}
