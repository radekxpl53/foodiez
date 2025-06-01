package org.foodiez.classes;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "full_price")
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
