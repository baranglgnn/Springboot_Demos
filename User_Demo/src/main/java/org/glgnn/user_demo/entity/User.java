package org.glgnn.user_demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String tc;

    @Column(nullable = false)
    private Boolean status;

    // 🔗 USER → PRODUCTS
    @OneToMany(
            mappedBy = "ownerUser",
            fetch = FetchType.LAZY
    )
    private List<Product> products = new ArrayList<>();

    protected User() {}

    public User(String name, String email, String tc) {
        this.name = name;
        this.email = email;
        this.tc = tc;
        this.status = true;
    }

    // GETTERS
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTc() { return tc; }
    public Boolean getStatus() { return status; }
    public List<Product> getProducts() { return products; }

    // DOMAIN METHODS
    public void changeName(String newName) {
        this.name = newName;
    }

    public void deactivate() {
        this.status = false;
    }
}
