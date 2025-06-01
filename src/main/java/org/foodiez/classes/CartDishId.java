package org.foodiez.classes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartDishId implements Serializable {

    private static final long serialVersionUID = 1L; // Dobra praktyka dla Serializable

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "dish_id")
    private Integer dishId; // Typ Integer, bo dish.id jest integer w bazie

    public CartDishId() {}

    public CartDishId(Long cartId, Integer dishId) {
        this.cartId = cartId;
        this.dishId = dishId;
    }

    // Gettery, Settery, hashCode, equals
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDishId that = (CartDishId) o;
        return Objects.equals(cartId, that.cartId) && Objects.equals(dishId, that.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, dishId);
    }
}