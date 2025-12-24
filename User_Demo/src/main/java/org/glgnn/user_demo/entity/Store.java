package org.glgnn.user_demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "store_seq"
    )
    @SequenceGenerator(
            name = "store_seq",
            sequenceName = "store_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    protected Store() {}

    public Store(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Product> getProducts() { return products; }
}
