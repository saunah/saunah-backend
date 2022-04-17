package ch.saunah.saunahbackend.model;

import javax.persistence.*;

/**
 * Model of a sauna type
 */
@Entity(name = "sauna")
public class Sauna {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    // TODO How to save pictures in DB?
    @Column(name = "picture", nullable = false)
    private boolean picture;

    @Column(name = "type", nullable = false)
    private boolean isMobile;

    @Column(name = "prize", nullable = false)
    private int prize;

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

    public  Sauna(){

    }

    public Sauna(String name, String description, boolean picture, boolean isMobile,
                int prize, int maxTemp, int numberOfPeople, String location, String street, int plz){
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.isMobile = isMobile;
        this.prize = prize;
        this.maxTemp = maxTemp;
        this.numberOfPeople = numberOfPeople;
        this.location = location;
        this.street = street;
        this.plz = plz;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getPicture() {
        return picture;
    }

    public boolean getType() {
        return isMobile;
    }

    public int getPrize() {
        return prize;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }

    public void setType(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }


}



