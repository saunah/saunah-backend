package ch.saunah.saunahbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * This class is used to define the table of the database of the SaunaImage entity.
 */
@Entity(name = "sauna_image")
public class SaunaImage {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name ="sauna_id", nullable = false)
    private Sauna sauna;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    /**
     * The default constructor for the SaunaImage.
     */
    public SaunaImage(){

    }

    public Integer getId() {
        return id;
    }

    public Sauna getSauna() {
        return sauna;
    }

    public void setSauna(Sauna sauna) {
        this.sauna = sauna;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
