package org.foodiez.controllers;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import org.foodiez.models.Dish;
import org.foodiez.util.Database;
import org.foodiez.util.InfoAlert;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DishesEditController implements Initializable {
    public Button unselectBtn;
    @FXML
    private TableView<Dish> dishesTable;
    @FXML
    private TableColumn<Dish, String> nameCol;
    @FXML
    private TableColumn<Dish, String> descriptionCol;
    @FXML
    private TableColumn<Dish, Double> priceCol;
    @FXML
    private TableColumn<Dish, String> categoryCol;

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField priceField;
    @FXML
    private ChoiceBox<String> categoryChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unselectBtn.setOnAction((ActionEvent e) -> clearSelectedItem());

        //inicjalizacja kolumn
        nameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("description"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Dish, Double>("price"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("category"));

        categoryChoice.getItems().addAll(Dish.categories);

        setDishesToTable();
    }

    public void setDishesToTable() {
        //pobranie dań z bazy danych i przypisanie do TableView;
        dishesTable.getItems().clear();

        List<Dish> result = Database.getSession().createQuery("FROM dishes", Dish.class).getResultList();
        dishesTable.getItems().addAll(FXCollections.observableList(result));
    }

    public void addDish(ActionEvent actionEvent) throws IOException {
        try {
            Dish dish = new Dish();
            dish.setName(nameField.getText());
            dish.setDescription(descriptionArea.getText());
            double price = Double.parseDouble(priceField.getText());
            if (price > 0) {
                dish.setPrice(price);
            } else {
                InfoAlert.infoAlert("Błąd!", "Cena nie może być ujemna!");
                return;
            }

            dish.setCategory(categoryChoice.getValue());

            Database.addToDatabase(dish);

            setDishesToTable();
        } catch (NumberFormatException e) {
            InfoAlert.infoAlert("Błąd!", "pola są puste!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editDish(ActionEvent actionEvent) {
        try {
            Dish dish = new Dish();
            dish.setName(nameField.getText());
            dish.setDescription(descriptionArea.getText());
            dish.setCategory(categoryChoice.getValue());

            double price = Double.parseDouble(priceField.getText());
            if (price > 0) {
                dish.setPrice(price);
            } else {
                InfoAlert.infoAlert("Błąd!", "Cena nie może być ujemna!");
                return;
            }

            Database.editItemDatabase(dish);

            setDishesToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeDish(ActionEvent actionEvent) {
        try {
            Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();

            Database.removeFromDatabase(selectedDish);

            setDishesToTable();
            clearSelectedItem();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectItemToEdit(MouseEvent mouseEvent) {
        Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();

        nameField.setText(selectedDish.getName());
        descriptionArea.setText(selectedDish.getDescription());
        priceField.setText(String.valueOf(selectedDish.getPrice()));
        categoryChoice.setValue(selectedDish.getCategory());
    }

    public void clearSelectedItem() {
        nameField.setText("");
        descriptionArea.setText("");
        priceField.setText("");
        categoryChoice.setValue("");

        dishesTable.getSelectionModel().clearSelection();
    }

}