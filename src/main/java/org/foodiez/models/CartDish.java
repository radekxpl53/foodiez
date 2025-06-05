package org.foodiez.models;

import jakarta.persistence.*;

@Entity(name = "carts_dishes")
@Table(name = "carts_dishes")
public class CartDish {

    @EmbeddedId
    private CartDishId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dishId")
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "ammount", nullable = false)
    private int ammount;

    @Column(name = "id", insertable = false, updatable = false)
    private Long sequentialId;

    public CartDish() {}

    public CartDish(Cart cart, Dish dish, int ammount) {
        this.cart = cart;
        this.dish = dish;
        this.ammount = ammount;
        this.id = new CartDishId(cart.getId(), dish.getId());
    }

    public CartDishId getId() { return id; }
    public void setId(CartDishId id) { this.id = id; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Dish getDish() { return dish; }
    public void setDish(Dish dish) { this.dish = dish; }

    public int getAmmount() { return ammount; }
    public void setAmmount(int ammount) {
        if (ammount <= 0) {
            this.ammount = 1;
        } else {
            this.ammount = ammount;
        }
    }
    public Long getSequentialId() { return sequentialId; }

}