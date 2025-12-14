package org.glgnn.user_demo.entity;
import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "tc")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String tc;

    @Column(nullable = false)
    private Boolean status;

    protected User() {
    }

    public User(String name, String email, String tc) {
        this.name = name;
        this.email = email;
        this.tc = tc;
        this.status = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTc() {
        return tc;
    }

    public Boolean getStatus() {
        return status;
    }
    public void changeName(String newName) {
        this.name = newName;
    }

    public void deactivate() {
        this.status = false;
    }

}

