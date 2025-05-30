package org.radek.restauracja.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.radek.restauracja.MainApplication;
import org.radek.restauracja.classes.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    public GridPane menuGrid;

    private final List<Danie> listaDanie = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDaniaToList();

        menuGrid.getColumnConstraints().clear(); // na wszelki wypadek
        menuGrid.getRowConstraints().clear();
        menuGrid.setHgap(20);
        menuGrid.setVgap(20);
        menuGrid.setPadding(new Insets(20));

        int column = 0; int row = 0;

        try {
            for (Danie danie : listaDanie) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radek/restauracja/item.fxml"));
                VBox itemBox = loader.load();
                ItemController controller = loader.getController();
                controller.setData(danie);

                menuGrid.add(itemBox, column, row);

                column++;
                if (column == 3) { // 3 elementy w wierszu
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    public void setDaniaToList() {
        //pobranie dań z bazy danych i przypisanie do TableView;
        listaDanie.clear();

        List<Danie> result = Database.getSession().createQuery("FROM danie", Danie.class).getResultList();
        listaDanie.addAll(result);
    }

}
