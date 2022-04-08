package ch.saunah.saunahbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "saunatype")
public class SaunaType {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sauna_name", nullable = false)
    private String saunaName;

    @Column(name = "sauna_description", nullable = false)
    private String saunaDescription;

    @Column(name = "sauna_Type", nullable = true)
    private boolean isMobile;

    @Column(name = "sauna_Location", nullable = true)
    private String saunaLocation;

    public SaunaType(String saunaName, String saunaDescription, boolean isMobile, String saunaLocation){
        this.saunaName = saunaName;
        this.saunaDescription = saunaDescription;
        this.isMobile = isMobile;
        this.saunaLocation = saunaLocation;
    }

    public void setSaunaName(String saunaName) {
        this.saunaName = saunaName;
    }

    public void setSaunaDescription(String SaunaDescription) {
        this.saunaDescription = saunaDescription;
    }

    public void setSaunaType(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public void setSaunaLocation(String saunaLocation) {
        this.saunaLocation = saunaLocation;
    }

    public Integer getSaunaId() {
        return id;
    }

    public String getSaunaName() {
        return saunaName;
    }

    public String getSaunaDescription() {
        return saunaDescription;
    }

    public boolean getSaunaType() {
        return isMobile;
    }

    public String getSaunaLocation() {
        return saunaLocation;
    }
}



