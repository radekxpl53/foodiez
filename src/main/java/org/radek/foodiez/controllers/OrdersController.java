package org.radek.foodiez.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.query.Query;
import org.radek.foodiez.classes.Database;
import org.radek.foodiez.classes.Order;
import org.radek.foodiez.util.DateFormatterUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> customerCol;
    @FXML
    private TableColumn<Order, String> dishCol;
    @FXML
    private TableColumn<Order, String> statusCol;
    @FXML
    private TableColumn<Order, String> dateCol;
    @FXML
    private RadioButton completedRadio;

    private Order selectedOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCol.setCellValueFactory(new PropertyValueFactory<Order, String>("customer"));
        dishCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDish().getName() + " " + cellData.getValue().getDish().getPrice() + " zł"));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getOrderDate())));

        setOrdersToTable();
    }

    public void setOrdersToTable() {
        ordersTable.getItems().clear();

        List<Order> result;

        if(completedRadio.isSelected()) {
            result = Database.getSession().createQuery("FROM orders ", Order.class).getResultList();
        } else {
            result = Database.getSession().createQuery("FROM orders WHERE status='oczekujące'", Order.class).getResultList();
        }

        ordersTable.getItems().addAll(result);
    }

    public void completeOrder(MouseEvent event) {
        Database.getSession().clear();
        Database.getSession().beginTransaction();

        Query orderQuery = Database.getSession().createQuery("UPDATE orders SET status=:status WHERE id=:id");
        orderQuery.setParameter("status", "zrealizowane");
        orderQuery.setParameter("id", selectedOrder.getId());
        orderQuery.executeUpdate();

        Database.getSession().getTransaction().commit();

        setOrdersToTable();
    }

    public void selectItemToEdit(MouseEvent event) {
        selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
    }

    public void checkedFilter(ActionEvent action) {
        setOrdersToTable();
    }

    public void deleteOrder(MouseEvent event) {
        Database.removeFromDatabase(selectedOrder);
        setOrdersToTable();
    }

}
