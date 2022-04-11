package ch.saunah.saunahbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "sauna")
public class Sauna {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    // TODO How to save pictures in DB?
    @Column(name = "picture", nullable = false)
    private boolean picture;

    @Column(name = "type", nullable = false)
    private boolean isMobile;

    @Column(name = "preis", nullable = false)
    private int preis;

    @Column(name = "max_temp", nullable = false)
    private int maxTemp;

    @Column(name = "numberOfPeople", nullable = false)
    private int numberOfPeople;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "plz", nullable = false)
    private int plz;

    public Sauna(long id, String name, String description, boolean picture, boolean isMobile,
                int preis, int maxTemp, int numberOfPeople, String location, String street, int plz){
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.isMobile = isMobile;
        this.preis = preis;
        this.maxTemp = maxTemp;
        this.numberOfPeople = numberOfPeople;
        this.location = location;
        this.street = street;
        this.plz = plz;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getType() {
        return isMobile;
    }

    public int getPreis() {
        return preis;
    }

    public int getMaxTemp() { return maxTemp; }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getLocation() {
        return location;
    }

    public String getStreet() {
        return street;
    }

    public int getPlz() {
        return plz;
    }
}



