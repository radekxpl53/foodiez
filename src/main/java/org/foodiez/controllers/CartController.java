package org.foodiez.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.foodiez.classes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    public VBox cartItemsVBox;
    @FXML
    public Label totalLabel;

    private Cart currentCart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadCart();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        displayCartItems();
    }

    private void loadCart() throws IOException {
        Session session = Database.getSession();
        try {
            currentCart = session.createQuery(
                            "FROM Cart c LEFT JOIN FETCH c.items WHERE c.customer = :customer AND c.status = 'ROBOCZY'", Cart.class)
                    .setParameter("customer", CurrentUser.getCustomer())
                    .uniqueResult();
            if (currentCart == null) {

                currentCart = new Cart(CurrentUser.getCustomer());
                currentCart.setItems(new java.util.ArrayList<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            InfoAlert.infoAlert("Błąd ładowania koszyka", "Nie udało się załadować koszyka: " + e.getMessage());
            currentCart = new Cart(CurrentUser.getCustomer());
            currentCart.setItems(new java.util.ArrayList<>());
        }
    }

    public void displayCartItems() {
        cartItemsVBox.getChildren().clear();
        double total = 0.0;

        if (currentCart != null && currentCart.getItems() != null) {
            for (CartDish cartDish : currentCart.getItems()) {
                if (cartDish.getDish() != null) {

                    VBox itemBox = new VBox();
                    itemBox.setSpacing(5);
                    itemBox.setPadding(new Insets(5));
                    itemBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5;");

                    Label nameLabel = new Label(cartDish.getDish().getName());
                    nameLabel.setStyle("-fx-font-weight: bold;");

                    Label quantityPriceLabel = new Label(
                            String.format("Ilość: %d x %.2f zł = %.2f zł",
                                    cartDish.getAmmount(),
                                    cartDish.getDish().getPrice(),
                                    cartDish.getAmmount() * cartDish.getDish().getPrice())
                    );

                    itemBox.getChildren().addAll(nameLabel, quantityPriceLabel);
                    cartItemsVBox.getChildren().add(itemBox);

                    total += cartDish.getAmmount() * cartDish.getDish().getPrice();
                }
            }
        }

        totalLabel.setText(String.format("Suma: %.2f zł", total));

        if (currentCart == null || currentCart.getItems().isEmpty()) {
            cartItemsVBox.getChildren().add(new Label("Twój koszyk jest pusty."));
        }
    }

    public void goToMenu() throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene("user-panel.fxml");
    }

    public void checkout() throws IOException {
        if (currentCart == null || currentCart.getItems().isEmpty()) {
            InfoAlert.infoAlert("Pusty koszyk", "Nie możesz złożyć zamówienia z pustym koszykiem.");
            return;
        }

        Order order = new Order();
        order.setCustomer(currentCart.getCustomer());
        order.setCart(currentCart);

        Session session = Database.getSession();
        Transaction tx = null;
        try {
            currentCart.setStatus("ZŁOŻONE");
            Database.editItemDatabase(currentCart);

            Query<Double> fullPriceQuery = session.createQuery("SELECT SUM(d.price * cd.ammount) FROM dishes d INNER JOIN carts_dishes cd ON d.id = cd.dish.id WHERE cd.cart.id = :id", Double.class);
            fullPriceQuery.setParameter("id", currentCart.getId());

            order.setFullPrice(fullPriceQuery.uniqueResult());
            order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));

            Database.addToDatabase(order);

            InfoAlert.infoAlert("Zamówienie złożone!", "Twoje zamówienie zostało pomyślnie złożone.");
            currentCart = new Cart(CurrentUser.getCustomer());
            currentCart.setItems(new java.util.ArrayList<>());
            displayCartItems();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            InfoAlert.infoAlert("Błąd zamówienia", "Nie udało się złożyć zamówienia: " + e.getMessage());
        }
    }


}