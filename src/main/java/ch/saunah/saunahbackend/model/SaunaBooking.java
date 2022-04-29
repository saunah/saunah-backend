package ch.saunah.saunahbackend.model;

import ch.saunah.saunahbackend.dto.BookingBody;

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

    public SaunaBooking(){

    }

    public int getId() {
        return id;
    }

    public String getSaunaName() {
        return saunaName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public BookingState getState() {
        return state;
    }

    public void setSaunaName(String saunaName) {
        this.saunaName = saunaName;
    }

    public void setBookingDate(Date creation) {
        this.bookingDate = creation;
    }

    public void setState(BookingState state) {
        this.state = state;
    }
}



