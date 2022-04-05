package ch.saunah.saunahbackend.user;

public class SignInBody {
    private final String email;
    private final String passwordHash;

    public SignInBody(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
