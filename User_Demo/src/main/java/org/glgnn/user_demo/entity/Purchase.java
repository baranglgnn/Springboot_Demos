package org.glgnn.user_demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "purchase_seq"
    )
    @SequenceGenerator(
            name = "purchase_seq",
            sequenceName = "purchase_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(name = "buyer_email", nullable = false)
    private String buyerEmail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "product_serial_number", nullable = false)
    private String productSerialNumber;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @Column
    private LocalDateTime returnDate;

    @Column(nullable = false)
    private Boolean active;

    protected Purchase() {}

    public Purchase(User buyer, Product product) {
        this.buyer = buyer;
        this.product = product;
        this.buyerEmail = buyer.getEmail();
        this.productSerialNumber = product.getSerialNumber();
        this.purchaseDate = LocalDateTime.now();
        this.active = true;
    }

    public Long getId() { return id; }
    public User getBuyer() { return buyer; }
    public Product getProduct() { return product; }
    public String getBuyerEmail() { return buyerEmail; }
    public String getProductSerialNumber() { return productSerialNumber; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public Boolean getActive() { return active; }

    public void returnProduct() {
        if (!this.active) {
            throw new IllegalStateException("Already returned");
        }
        this.active = false;
        this.returnDate = LocalDateTime.now();
    }
}
