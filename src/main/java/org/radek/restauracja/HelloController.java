package org.radek.restauracja;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private static List<Danie> dania = new ArrayList<Danie>();
    @FXML
    private TextField idInput;
    @FXML
    private TextField nazwaInput;
    @FXML
    private TextField opisInput;
    @FXML
    private TextField cenaInput;
    @FXML
    private TextField kategoriaInput;
    @FXML
    private ListView<Danie> listDania;
    @FXML
    private TableView<Danie> tableDania;
    @FXML
    private TableColumn<Danie, String> nazwaColumn;
    @FXML
    private TableColumn<Danie, String> opisColumn;
    @FXML
    private TableColumn<Danie, Double> cenaColumn;
    @FXML
    private TableColumn<Danie, String> kategoriaColumn;

    @FXML
    public void refreshListView() {


        //listDania.setItems(FXCollections.observableList(DatabaseConnection.getSession().createQuery("FROM danie", Danie.class).getResultList()));
        dania = Database.getSession().createQuery("FROM danie", Danie.class).getResultList();
        tableDania.getItems().clear();
        for (Danie danie : dania) {
            tableDania.getItems().add(danie);
        }

    }

    @FXML
    public void onHelloButtonClick(ActionEvent e) {
        Danie danie = new Danie();
        danie.setNazwa(nazwaInput.getText());
        danie.setOpis(opisInput.getText());
        danie.setCena(Double.parseDouble(cenaInput.getText()));
        danie.setKategoria(kategoriaInput.getText());

        Configuration config = new Configuration().configure().addAnnotatedClass(Danie.class);
        SessionFactory factory = config.buildSessionFactory();

        Database.addToDatabase(danie);

        System.out.println(danie);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazwaColumn.setCellValueFactory(new PropertyValueFactory<Danie, String>("nazwa"));
        opisColumn.setCellValueFactory(new PropertyValueFactory<Danie, String>("opis"));
        cenaColumn.setCellValueFactory(new PropertyValueFactory<Danie, Double>("cena"));
        kategoriaColumn.setCellValueFactory(new PropertyValueFactory<Danie, String>("kategoria"));
    }
}