package ch.saunah.saunahbackend.model;

/**
 * This enum defines all specified sauna types.
 */
public enum SaunaType {
    TENT("TENT"),
    TRAILER("TRAILER");

    private final String type;

    /**
     * The constructor which sets the type value.
     * @param type
     */
    SaunaType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
