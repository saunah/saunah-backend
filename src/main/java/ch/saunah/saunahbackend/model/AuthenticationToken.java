package ch.saunah.saunahbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "autentication_token")
public class AuthenticationToken {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "userId", nullable = false)
    @JoinColumn(nullable = false, name = "user_id")
    private String userId;

    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    public AuthenticationToken(Integer id, String token, String userId, Date creationDate) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.creationDate = creationDate;
    }

    public AuthenticationToken() {

    }
}
