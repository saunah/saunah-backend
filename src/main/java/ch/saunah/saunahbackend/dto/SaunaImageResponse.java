package ch.saunah.saunahbackend.dto;

/**
 * This class is used as the response DTO object, when the sauna images was retrieved.
 */
public class SaunaImageResponse {
    private final int id;
    private final int saunaId;
    private final String url;

    /**
     * This constructor sets all the fields of this object.
     *
     * @param id the id of the image
     * @param saunaId the id of the saune
     * @param url the url of the image
     */
    public SaunaImageResponse(int id, int saunaId, String url){
        this.id = id;
        this.saunaId = saunaId;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public int getSaunaId() {
        return saunaId;
    }

    public String getUrl() {
        return url;
    }
}
