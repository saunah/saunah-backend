package ch.saunah.saunahbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class is used to define the table of the database of the SaunaImage entity.
 */
@Entity(name = "sauna_image")
public class SaunaImage {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sauna_id", nullable = false)
    private Integer saunaId;

    @Column(name = "file_path", nullable = false, unique = true)
    private String filePath;

    /**
     * The default constructor for the SaunaImage.
     */
    public SaunaImage(){

    }

    public Integer getId() {
        return id;
    }

    public Integer getSaunaId() {
        return saunaId;
    }

    public void setSaunaId(Integer saunaId) {
        this.saunaId = saunaId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
