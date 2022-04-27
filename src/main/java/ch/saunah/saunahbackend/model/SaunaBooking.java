package ch.saunah.saunahbackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "creation", nullable = false)
    private Date creation;

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

    public Date getCreation() {
        return creation;
    }

    public BookingState getState() {
        return state;
    }

    public void setSaunaName(String saunaName) { this.saunaName = saunaName; }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public void setState(BookingState state) {
        this.state = state;
    }
}



