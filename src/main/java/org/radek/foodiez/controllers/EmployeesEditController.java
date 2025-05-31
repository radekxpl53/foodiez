package org.radek.foodiez.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;
import org.radek.foodiez.classes.Database;
import org.radek.foodiez.classes.InfoAlert;
import org.radek.foodiez.classes.Employee;
import org.radek.foodiez.exceptions.WrongEmailException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesEditController implements Initializable {

    Employee selectedEmployee;

    @FXML
    private TableView<Employee> employeesTable;
    @FXML
    private TableColumn<Employee, String> nameCol;
    @FXML
    private TableColumn<Employee, String> surnameCol;
    @FXML
    private TableColumn<Employee, String> emailCol;
    @FXML
    private TableColumn<Employee, String> phoneCol;
    @FXML
    private TableColumn<Employee, String> roleCol;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ChoiceBox<String> roleChoice;
    @FXML
    private Button unselectBtn;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setDisable(true);

        errorLabel.setVisible(false);

        unselectBtn.setOnAction((ActionEvent e) -> clearSelectedItem());

        //inicjalizacja kolumn
        nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
        roleCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("role"));

        roleChoice.getItems().setAll(Employee.roles);

        setEmployeesToTable();
    }

    public void setEmployeesToTable() {
        //pobranie pracowników z bazy danych i przypisanie do TableView
        employeesTable.getItems().clear();

        List<Employee> result = Database.getSession().createQuery("FROM employees WHERE role != 'admin'", Employee.class).getResultList();
        employeesTable.getItems().addAll(FXCollections.observableList(result));
    }

    public void clearSelectedItem() {
        idField.setText("");
        nameField.setText("");
        surnameCol.setText("");
        emailField.setText("");
        phoneCol.setText("");
        roleChoice.setValue("");

        employeesTable.getSelectionModel().clearSelection();
    }

    public void selectItemToEdit(MouseEvent mouseEvent) {
        selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();

        idField.setText(String.valueOf(selectedEmployee.getId()));
        nameCol.setText(selectedEmployee.getName());
        surnameCol.setText(selectedEmployee.getSurname());
        emailField.setText(selectedEmployee.getEmail());
        phoneCol.setText(selectedEmployee.getPhone());
        roleChoice.setValue(selectedEmployee.getRole());
    }

    public void editEmployee(ActionEvent actionEvent) {
        try {
            Employee employee = Database.getSession().get(Employee.class, selectedEmployee.getId());

            if (employee != null) {
                employee.setName(nameField.getText());
                employee.setSurname(surnameField.getText());
                employee.setEmail(emailField.getText());
                employee.setPhone(phoneField.getText());
                employee.setRole(roleChoice.getValue());
                Database.editItemDatabase(employee);
            }

            setEmployeesToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        } catch (WrongEmailException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    public void addEmployee(ActionEvent actionEvent) {
        try {
            Employee employee = new Employee();
            employee.setName(nameField.getText());
            employee.setSurname(surnameField.getText());
            if (EmailValidator.getInstance().isValid(emailField.getText())) {
                employee.setEmail(emailField.getText());
            } else throw new WrongEmailException();
            employee.setPhone(phoneField.getText());
            employee.setRole(roleChoice.getValue());

            Database.addToDatabase(employee);

            setEmployeesToTable();
        } catch (NumberFormatException e) {
            InfoAlert.emptyFieldAlert();
        } catch (WrongEmailException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    public void setLoginAndPasswd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/radek/restauracja/set-up-login-password.fxml"));
        Parent root = fxmlLoader.load();
        SetUpEmployeeController controller = fxmlLoader.getController();

        controller.setTempPracownik(selectedEmployee);
        Stage stage = new Stage();

        stage.setTitle("Przypisz login i hasło");
        stage.initModality(Modality.NONE);
        stage.setScene(new Scene(root));

        stage.show();
    }
}
