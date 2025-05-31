package org.radek.foodiez.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.radek.foodiez.classes.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ItemController {

    @FXML
    private Label priceLabel;
    @FXML
    private Label nameLabel;

    private Dish dish;

    public void setData(Dish dish) {
        this.dish = dish;
        nameLabel.setText(dish.getName());
        priceLabel.setText(dish.getPrice() + "zł");
    }

    public void orderDanie(MouseEvent mouseEvent) throws IOException {
        Order order = new Order();
        order.setDish(dish);
        order.setCustomer(CurrentUser.getCustomer());
        order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));

        Database.addToDatabase(order);

        InfoAlert.infoAlert("Złożono zamówienie!", dish.getName() + " " + dish.getPrice() + "zł");
    }

}
