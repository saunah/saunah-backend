package ch.saunah.saunahbackend.model;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
