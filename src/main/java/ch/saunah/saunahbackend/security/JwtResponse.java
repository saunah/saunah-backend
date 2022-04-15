package ch.saunah.saunahbackend.security;

import java.io.Serializable;

/**
 * The JWT response object which contains the JWT token.
 */
public class JwtResponse  implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;

    /**
     * Sets the JWT token.
     * @param jwtToken the JWT token
     */
    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
