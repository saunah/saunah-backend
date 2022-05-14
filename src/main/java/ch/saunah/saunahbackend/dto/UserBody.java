package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.UserRole;

/**
 * This class is used as the DTO object when registering an account.
 */
public class UserBody {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String street;
    private String place;
    private String zip;
    private UserRole role;
    private boolean initialAdmin;
    private boolean isDeleted;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean getInitialAdmin() {return initialAdmin; }

    public  void setInitialAdmin(boolean initialAdmin) { this.initialAdmin = initialAdmin;}

    public boolean getIsDeleted() {return isDeleted; }

    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted;}
}
