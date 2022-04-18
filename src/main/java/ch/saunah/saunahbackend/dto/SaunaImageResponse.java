package ch.saunah.saunahbackend.dto;

public class SaunaImageResponse {
    private final int id;
    private final int saunaId;
    private final String fileName;

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
