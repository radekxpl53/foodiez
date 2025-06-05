package org.foodiez.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity(name = "orders")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(optional = true)
    @JoinColumn(name = "cart_id", nullable = true)
    private Cart cart;

    @Column(nullable = false)
    private String status = "oczekujące";

    @Column(name = "order_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp orderDate;

    @Column(name = "full_price", length = 10, precision = 2)
    private Double fullPrice;


    public Long getId() { return id; }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Timestamp getOrderDate() { return orderDate; }

    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public Double getFullPrice() { return fullPrice; }

    public void setFullPrice(Double fullPrice) { this.fullPrice = fullPrice; }

}
