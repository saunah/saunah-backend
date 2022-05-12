package ch.saunah.saunahbackend.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @Column(name = "description")
    private String description;

    @Column(name = "isMobile", nullable = false)
    private boolean isMobile;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "max_temp", nullable = false)
    private int maxTemp;

    @Column(name = "numberOfPeople", nullable = false)
    private int numberOfPeople;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "zip", nullable = false)
    private int zip;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy="sauna", cascade = CascadeType.ALL)
    private Set<SaunaImage> saunaImages = new HashSet<>();

    @Column(name = "google_calender_id", nullable = true)
    private String googleCalenderID;

    public Sauna(){

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

    public boolean isMobile() {
        return isMobile;
    }

    public int getPrice() {
        return price;
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

    public int getZip() {
        return zip;
    }

    public String getType() {
        return type;
    }

    public String getGoogleCalenderID(){return  googleCalenderID;}

    public List<SaunaImage> getSaunaImages(){
        return saunaImages.stream().collect(Collectors.toList());
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGoogleCalenderID(String googleCalenderID) {this.googleCalenderID = googleCalenderID;}

    public void addSaunaImage(SaunaImage saunaImage){
        this.saunaImages.add(saunaImage);
    }
}



