package org.radek.restauracja;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DanieEditController implements Initializable {
    @FXML
    private TableColumn<Danie, Integer> idCol;
    @FXML
    private TableColumn<Danie, String> nazwaCol;
    @FXML
    private TableColumn<Danie, String> opisCol;
    @FXML
    private TableColumn<Danie, Double> cenaCol;
    @FXML
    private TableColumn<Danie, String> kategoriaCol;

    @FXML
    private TextField idField;
    @FXML
    private TextField nazwaField;
    @FXML
    private TextField opisField;
    @FXML
    private TextField cenaField;
    @FXML
    private ChoiceBox<String> kategoriaChoice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kategoriaChoice.getItems().addAll(Danie.kategorie);
    }
}