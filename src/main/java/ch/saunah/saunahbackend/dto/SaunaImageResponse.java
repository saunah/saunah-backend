package ch.saunah.saunahbackend.dto;

/**
 * This class is used as the response DTO object, when the sauna images was retrieved.
 */
public class SaunaImageResponse {
    private final int id;
    private final int saunaId;
    private final String fileName;

    /**
     * This constructor sets all the fields of this object.
     *
     * @param id the id of the image
     * @param saunaId the id of the saune
     * @param fileName the fileName of the image
     */
    public SaunaImageResponse(int id, int saunaId, String fileName){
        this.id = id;
        this.saunaId = saunaId;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public String getFileName() {
        return fileName;
    }
}
