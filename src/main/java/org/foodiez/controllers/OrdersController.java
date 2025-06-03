package org.foodiez.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.foodiez.classes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.foodiez.util.DateFormatterUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> customerCol;
    @FXML
    private TableColumn<Order, String> fullPriceCol;
    @FXML
    private TableColumn<Order, String> statusCol;
    @FXML
    private TableColumn<Order, String> dateCol;
    @FXML
    private RadioButton completedRadio;
    @FXML
    private Button completeOrderButton;
    @FXML
    private Button deleteOrderButton;

    private Order selectedOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getLogin())
        );

        fullPriceCol.setCellValueFactory(cellData ->
            new SimpleStringProperty((Double)cellData.getValue().getFullPrice() + " zł")
        );

        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getOrderDate())));

        setButtonsDisabled(true);

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedOrder = newSelection;
            setButtonsDisabled(newSelection == null);
        });

        try {
            setOrdersToTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setButtonsDisabled(boolean disabled) {
        completeOrderButton.setDisable(disabled);
        deleteOrderButton.setDisable(disabled);

        if (!disabled && selectedOrder != null && "zrealizowane".equals(selectedOrder.getStatus())) {
            completeOrderButton.setDisable(true);
        }
    }

    public void setOrdersToTable() throws IOException {
        ordersTable.getItems().clear();
        Session session = null;
        try {
            session = Database.getSession();
            List<Order> result;

            if (completedRadio.isSelected()) {
                result = session.createQuery("FROM orders", Order.class).getResultList();
            } else {
                result = session.createQuery("FROM orders WHERE status='oczekujące'", Order.class).getResultList();
            }
            ordersTable.getItems().addAll(result);
        } catch (Exception e) {
            e.printStackTrace();
            InfoAlert.infoAlert("Błąd ładowania zamówień", "Nie udało się załadować listy zamówień: " + e.getMessage());
        }
    }

    @FXML
    public void completeOrder(MouseEvent event) throws IOException {
        if (selectedOrder == null) {
            InfoAlert.infoAlert("Błąd", "Wybierz zamówienie do zrealizowania.");
            return;
        }

        if ("zrealizowane".equals(selectedOrder.getStatus())) {
            InfoAlert.infoAlert("Informacja", "To zamówienie jest już zrealizowane.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź realizację");
        alert.setHeaderText("Czy na pewno chcesz oznaczyć zamówienie jako zrealizowane?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Order orderToUpdate = Database.getSession().get(Order.class, selectedOrder.getId());
                if (orderToUpdate != null) {
                    orderToUpdate.setStatus("zrealizowane");
                    Database.editItemDatabase(orderToUpdate);
                    InfoAlert.infoAlert("Sukces", "Zamówienie zostało oznaczone jako zrealizowane.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                InfoAlert.infoAlert("Błąd", "Nie udało się zrealizować zamówienia: " + e.getMessage());
            }
            setOrdersToTable();
            setButtonsDisabled(true);
        }
    }

    @FXML
    public void selectItemToEdit(MouseEvent event) {
        selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        setButtonsDisabled(selectedOrder == null);
    }

    @FXML
    public void checkedFilter(ActionEvent action) throws IOException {
        setOrdersToTable();
        ordersTable.getSelectionModel().clearSelection();
        setButtonsDisabled(true);
    }

    @FXML
    public void deleteOrder(MouseEvent event) throws IOException {
        if (selectedOrder == null) {
            InfoAlert.infoAlert("Błąd", "Wybierz zamówienie do usunięcia.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź usunięcie");
        alert.setHeaderText("Czy na pewno chcesz usunąć wybrane zamówienie?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Order orderToDelete = Database.getSession().get(Order.class, selectedOrder.getId());
                if (orderToDelete != null) {
                    Database.removeFromDatabase(orderToDelete);
                    InfoAlert.infoAlert("Sukces", "Zamówienie zostało usunięte.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                InfoAlert.infoAlert("Błąd", "Nie udało się usunąć zamówienia: " + e.getMessage());
            }

            setOrdersToTable();
            setButtonsDisabled(true);
        }
    }
}