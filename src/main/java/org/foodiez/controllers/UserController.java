package org.foodiez.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.foodiez.classes.CurrentUser;
import org.foodiez.classes.Database;
import org.foodiez.classes.Dish;
import org.hibernate.query.Query;
import org.foodiez.classes.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    public GridPane menuGrid;
    @FXML
    public Label currentUserLabel;
    @FXML
    public ChoiceBox<String> filterChoice;

    private final List<Dish> dishes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUserLabel.setText("zalogowano jako: " + CurrentUser.getCustomer().getLogin());

        filterChoice.getItems().add("Wszystko");
        filterChoice.getItems().addAll(Dish.categories);

        filterChoice.setValue(filterChoice.getItems().getFirst());

        setDishesToList();
        showItems();

        filterChoice.setOnAction(event -> {setDishesToList(); showItems();});
    }

    public void setDishesToList() {
        dishes.clear();

        String filter = filterChoice.getValue();
        List<Dish> result;

        if (filter.equals("Wszystko")) {
            result = Database.getSession().createQuery("FROM dishes", Dish.class).getResultList();
        } else {
            Query<Dish> filteredQuery = Database.getSession().createQuery("FROM dishes WHERE category = :category", Dish.class);
            filteredQuery.setParameter("category", filter);

            result = filteredQuery.getResultList();
        }
        dishes.addAll(result);
    }

    public void showItems() {
        menuGrid.getChildren().clear();

        menuGrid.getColumnConstraints().clear();
        menuGrid.getRowConstraints().clear();
        menuGrid.setHgap(20);
        menuGrid.setVgap(20);
        menuGrid.setPadding(new Insets(20));

        int column = 0; int row = 0;

        try {
            for (Dish dish : dishes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/foodiez/item.fxml"));
                VBox itemBox = loader.load();
                ItemController controller = loader.getController();
                controller.setData(dish);

                menuGrid.add(itemBox, column, row);

                column++;
                if (column == 4) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene("main-window.fxml");
    }

    public void goToCart() throws IOException {
        SceneController sc = new SceneController();
        sc.switchScene("cart-view.fxml");
    }

}
