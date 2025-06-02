package org.foodiez.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.foodiez.classes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class ItemController {

    @FXML
    private Label priceLabel;
    @FXML
    private Label nameLabel;

    private Dish dish;

    public void setData(Dish dish) {
        this.dish = dish;
        nameLabel.setText(dish.getName());
        priceLabel.setText(String.format("%.2f zł", dish.getPrice()));
    }

    @FXML
    public void addToCart(MouseEvent mouseEvent) throws IOException {
        Customer currentUser = CurrentUser.getCustomer();
        if (currentUser == null) {
            InfoAlert.infoAlert("Błąd", "Nie jesteś zalogowany!");
            return;
        }

        try {

            Cart cart = Database.getSession().createQuery("FROM Cart c LEFT JOIN FETCH c.items WHERE c.customer = :customer AND c.status = 'ROBOCZY'", Cart.class)
                    .setParameter("customer", currentUser)
                    .uniqueResult();

            if (cart == null) {
                cart = new Cart(currentUser);
                Database.addToDatabase(cart);
            }

            boolean itemExistsInCart = false;
            for (CartDish cd : cart.getItems()) {
                if (cd.getDish().getId().equals(dish.getId())) {
                    cd.setAmmount(cd.getAmmount() + 1);
                    itemExistsInCart = true;
                    Database.editItemDatabase(cd);
                    break;
                }
            }

            if (!itemExistsInCart) {
                CartDish newCartItem = new CartDish(cart, dish, 1); // Domyślnie dodaj 1 sztukę
                cart.getItems().add(newCartItem);
            }

            Database.editItemDatabase(cart);

            InfoAlert.infoAlert("Dodano do koszyka!", dish.getName() + " dodano do Twojego koszyka.");
        } catch (Exception e) {
            e.printStackTrace();
            InfoAlert.infoAlert("Błąd", "Nie udało się dodać do koszyka: " + e.getMessage());
        }
    }
}