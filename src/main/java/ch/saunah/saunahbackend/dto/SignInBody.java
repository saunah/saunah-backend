package ch.saunah.saunahbackend.dto;

/**
 * This class is used as the DTO object when logging in.
 */
public class SignInBody {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
