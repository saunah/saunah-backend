package ch.saunah.saunahbackend.model;

/**
 * This enum defines all specified user roles.
 */
public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    /**
     * The constructor which sets the role value.
     * @param role
     */
    UserRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
