package org.foodiez.models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    @Column(length = 20, nullable = false)
    private String status = "ROBOCZY";

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp creationDate;

    @Version // Dla kolumny 'version' do optymistycznego blokowania
    private Integer version;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER // Można rozważyć LAZY w zależności od potrzeb
    )
    private List<CartDish> items = new ArrayList<>();

    // Konstruktory, Gettery, Settery
    public Cart() {
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Cart(Customer customer) {
        this();
        this.customer = customer;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreationDate() { return creationDate; }
    public void setCreationDate(Timestamp creationDate) { this.creationDate = creationDate; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public List<CartDish> getItems() { return items; }
    public void setItems(List<CartDish> items) { this.items = items; }

    // Metody pomocnicze
    public void addItem(CartDish item) {
        // Sprawdź, czy danie już istnieje w koszyku, jeśli tak - zwiększ ilość
        for (CartDish existingItem : this.items) {
            if (existingItem.getDish().getId().equals(item.getDish().getId())) {
                existingItem.setAmmount(existingItem.getAmmount() + item.getAmmount());
                return;
            }
        }
        // Jeśli nie, dodaj nowy
        this.items.add(item);
        item.setCart(this);
    }

    public void removeItem(Dish dishToRemove) {
        this.items.removeIf(item -> item.getDish().getId().equals(dishToRemove.getId()));
    }

    public void clearCart() {
        this.items.clear();
    }
}