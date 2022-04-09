package ch.saunah.saunahbackend.user;

public class SignUpResponse {
    private final String state;

    public SignUpResponse(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
