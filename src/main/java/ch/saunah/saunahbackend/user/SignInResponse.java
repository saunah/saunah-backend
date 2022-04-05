package ch.saunah.saunahbackend.user;

public class SignInResponse {
    private final UserReturnCode state;
    private final String token;

    public SignInResponse(UserReturnCode state, String token) {
        this.state = state;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public UserReturnCode getState() {
        return state;
    }
}
