package ch.saunah.saunahbackend.model;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity(name = "appuser")
public class User {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email_address", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = true)
    @Schema(nullable = true)
    private String password_hash;

    public User(String name, String emailAddress){
        Objects.requireNonNull(name, "Name must not be null!");
        Objects.requireNonNull(emailAddress, "E-Mail Address must not be null!");
        this.name = name;
        this.email = emailAddress;
        this.password_hash = "root";
    }

    public User(){

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword_hash() {
        return password_hash;
    }
}
