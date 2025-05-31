package org.radek.foodiez.controllers;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import org.radek.foodiez.classes.Dish;
import org.radek.foodiez.classes.Database;
import org.radek.foodiez.classes.InfoAlert;

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
    private TextField idField;
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
        idField.setDisable(true);

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

    public void addDish(ActionEvent actionEvent) {
        try {
            Dish dish = new Dish();
            dish.setName(nameField.getText());
            dish.setDescription(descriptionArea.getText());
            dish.setPrice(Double.parseDouble(priceField.getText()));
            dish.setCategory(categoryChoice.getValue());

            Database.addToDatabase(dish);

            setDishesToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        }
    }

    public void editDish(ActionEvent actionEvent) {
        try {
            Dish dish = new Dish();
            dish.setId(Integer.parseInt(idField.getText()));
            dish.setName(nameField.getText());
            dish.setDescription(descriptionArea.getText());
            dish.setPrice(Double.parseDouble(priceField.getText()));
            dish.setCategory(categoryChoice.getValue());

            Database.editItemDatabase(dish);

            setDishesToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
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

        idField.setText(String.valueOf(selectedDish.getId()));
        nameField.setText(selectedDish.getName());
        descriptionArea.setText(selectedDish.getDescription());
        priceField.setText(String.valueOf(selectedDish.getPrice()));
        categoryChoice.setValue(selectedDish.getCategory());
    }

    public void clearSelectedItem() {
        idField.setText("");
        nameField.setText("");
        descriptionArea.setText("");
        priceField.setText("");
        categoryChoice.setValue("");

        dishesTable.getSelectionModel().clearSelection();
    }

}