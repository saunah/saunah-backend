package ch.saunah.saunahbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class is used to define the table of the database of the user entity.
 */
@Entity(name = "appuser")
public class User {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "activationId", nullable = false, unique = true)
    private String activationId;

    @Column(name="firstName", nullable = false)
    private String firstName;

    @Column(name="lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "resetpasswordhash")
    private String resetpasswordhash;

    @Column(name = "initialAdmin")
    private boolean initialAdmin;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    /**
     * The default constructor for the user.
     */
    public User(){

    }

    public Integer getId() {
        return id;
    }

    public String getActivationId() {
        return activationId;
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

    public String getPasswordHash() {
        return passwordHash;
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

    public void setActivationId(String activationId) {
        this.activationId = activationId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getResetpasswordHash(){return resetpasswordhash;}

    public void setResetpasswordHash(String resetpassword_hash){this.resetpasswordhash = resetpassword_hash;}

    public boolean getInitialAdmin() {return initialAdmin; }

    public  void setInitialAdmin(boolean initialAdmin) { this.initialAdmin = initialAdmin;}

    public boolean getIsDeleted() {return isDeleted; }

    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted;}
}
