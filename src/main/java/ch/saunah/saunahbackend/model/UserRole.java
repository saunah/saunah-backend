package ch.saunah.saunahbackend.model;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
