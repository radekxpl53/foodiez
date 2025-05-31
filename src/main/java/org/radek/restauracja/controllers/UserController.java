package org.radek.restauracja.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.hibernate.query.Query;
import org.radek.restauracja.classes.*;

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

    private final List<Danie> listaDanie = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUserLabel.setText("zalogowano jako: " + CurrentUser.getKlient().getLogin());

        filterChoice.getItems().add("Wszystko");
        filterChoice.getItems().addAll(Danie.kategorie);

        filterChoice.setValue(filterChoice.getItems().getFirst());

        setDaniaToList();
        showItems();

        //zmiana filtra
        filterChoice.setOnAction(event -> {setDaniaToList(); showItems();});
    }

    public void setDaniaToList() {
        //pobranie dań z bazy danych i przypisanie do TableView;
        listaDanie.clear();

        String filter = filterChoice.getValue();
        List<Danie> result;

        if (filter.equals("Wszystko")) {
            result = Database.getSession().createQuery("FROM danie", Danie.class).getResultList();
        } else {
            Query<Danie> filteredQuery = Database.getSession().createQuery("FROM danie WHERE kategoria = :kategoria", Danie.class);
            filteredQuery.setParameter("kategoria", filter);

            result = filteredQuery.getResultList();
        }
        listaDanie.addAll(result);
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
            for (Danie danie : listaDanie) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radek/restauracja/item.fxml"));
                VBox itemBox = loader.load();
                ItemController controller = loader.getController();
                controller.setData(danie);

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

}
