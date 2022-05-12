package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;

/**
 * This class is used as the response DTO object, when user data was retrieved.
 */
public class UserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String place;
    private String zip;
    private boolean activated;
    private UserRole role;

    /**
     * This constructor sets all the fields of this object.
     */
    public UserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.street = user.getStreet();
        this.place = user.getPlace();
        this.zip = user.getZip();
        this.activated = user.isActivated();
        this.role = user.getRole();
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getPlace() {
        return place;
    }

    public String getZip() {
        return zip;
    }

    public boolean isActivated() {
        return activated;
    }

    public UserRole getRole() {
        return role;
    }

}
