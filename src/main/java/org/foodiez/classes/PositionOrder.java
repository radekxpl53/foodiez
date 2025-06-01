package org.foodiez.classes;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "positions_orders")
public class PositionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Zakładając, że 'id' w 'positions_orders' jest auto-inkrementowane
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) // LAZY, aby nie ładować Order za każdym razem, gdy ładujemy pozycję
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Column(name = "ammount", nullable = false) // Używam "ammount" zgodnie z Twoją bazą
    private int ammount; // Zmieniono na 'amount' dla poprawności, ale jeśli wolisz 'ammount' - zmień

    @Column(name = "unit_price", nullable = false, precision = 38, scale = 2)
    private BigDecimal unitPrice;

    // Konstruktory, Gettery, Settery

    public PositionOrder() {}

    public PositionOrder(Order order, Dish dish, int ammount, BigDecimal unitPrice) {
        this.order = order;
        this.dish = dish;
        this.ammount = ammount;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Dish getDish() { return dish; }
    public void setDish(Dish dish) { this.dish = dish; }

    public int getAmmount() { return ammount; }
    public void setAmmount(int ammount) { this.ammount = ammount; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}