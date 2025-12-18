package org.glgnn.user_demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "serial_number")
        }
)
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_seq"
    )
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    protected Product() {}

    public Product(String productName, String serialNumber, Double price) {
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.price = price;
        this.status = true;
    }

    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public String getSerialNumber() { return serialNumber; }
    public Boolean getStatus() { return status; }
    public Double getPrice() { return price; }
    public User getOwnerUser() { return ownerUser; }

    public void assignToUser(User user) {
        this.ownerUser = user;
    }

    public void unassignUser() {
        this.ownerUser = null;
    }

    public void deactivate() {
        this.status = false;
    }

    public void changePrice(Double newPrice) {
        this.price = newPrice;
    }
}
