package org.foodiez.controllers;

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
import org.foodiez.util.Database;
import org.foodiez.models.Employee;
import org.foodiez.exceptions.EmptyFieldException;
import org.foodiez.exceptions.WrongEmailException;
import org.foodiez.exceptions.WrongPhoneException;
import org.foodiez.util.PhoneNumber;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesEditController implements Initializable {

    Employee selectedEmployee;

    @FXML
    public Button setupButton;
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

    //pobranie pracowników z bazy danych i przypisanie do TableView
    public void setEmployeesToTable() {
        employeesTable.getItems().clear();

        List<Employee> result = Database.getSession().createQuery("FROM employees WHERE role != 'admin'", Employee.class).getResultList();
        employeesTable.getItems().addAll(FXCollections.observableList(result));
    }

    //zerowanie wybranego elementu
    public void clearSelectedItem() {

        nameField.setText("");
        surnameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        roleChoice.setValue("");

        setupButton.setDisable(false);

        employeesTable.getSelectionModel().clearSelection();
    }

    //przypisanie wybranego elementu do bloków wejściowych
    public void selectItemToEdit(MouseEvent mouseEvent) {
        selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();

        if(selectedEmployee != null) {
            if (selectedEmployee.getLogin() != null) {
                setupButton.setDisable(true);
            } else {
                setupButton.setDisable(false);
            }

            nameField.setText(selectedEmployee.getName());
            surnameField.setText(selectedEmployee.getSurname());
            emailField.setText(selectedEmployee.getEmail());
            phoneField.setText(selectedEmployee.getPhone());
            roleChoice.setValue(selectedEmployee.getRole());
        }


    }

    //edycja pracownika
    public void editEmployee(ActionEvent actionEvent) throws IOException {
        try {
            Employee employee = Database.getSession().get(Employee.class, selectedEmployee.getId());

            if (employee != null) {
                if (!nameField.getText().isEmpty()) {
                    employee.setName(nameField.getText());
                } else throw new EmptyFieldException("imie");

                if (!surnameField.getText().isEmpty()) {
                    employee.setSurname(surnameField.getText());
                } else throw new EmptyFieldException("nazwisko");

                if (EmailValidator.getInstance().isValid(emailField.getText())) {
                    employee.setEmail(emailField.getText());
                } else throw new WrongEmailException();

                String phone = phoneField.getText();
                if (PhoneNumber.isValid(phone)) {
                    employee.setPhone(phoneField.getText());
                } else throw new WrongPhoneException();

                employee.setRole(roleChoice.getValue());

                Database.addToDatabase(employee);

                setEmployeesToTable();

                errorLabel.setVisible(false);
            }

            setEmployeesToTable();
        } catch (WrongEmailException | WrongPhoneException | EmptyFieldException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    //dodawanie pracownika
    public void addEmployee(ActionEvent actionEvent) {
        try {
            Employee employee = new Employee();
            if (!nameField.getText().isEmpty()) {
                employee.setName(nameField.getText());

            } else throw new EmptyFieldException("imie");

            if (!surnameField.getText().isEmpty()) {
                employee.setSurname(surnameField.getText());
            } else throw new EmptyFieldException("nazwisko");

            if (EmailValidator.getInstance().isValid(emailField.getText())) {
                employee.setEmail(emailField.getText());
            } else throw new WrongEmailException();

            String phone = phoneField.getText();
            if (PhoneNumber.isValid(phone)) {
                employee.setPhone(phoneField.getText());
            } else throw new WrongPhoneException();

            employee.setRole(roleChoice.getValue());

            Database.addToDatabase(employee);

            setEmployeesToTable();

            errorLabel.setVisible(true);

        } catch (WrongEmailException | WrongPhoneException | EmptyFieldException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    public void removeEmployee(ActionEvent actionEvent) {
        Employee employee = Database.getSession().get(Employee.class, selectedEmployee.getId());

        if (employee != null) {
            Database.removeFromDatabase(employee);
        }

        setEmployeesToTable();
    }

    //przypisywanie loginu i hasła pracownikowi
    public void setLoginAndPasswd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/foodiez/set-up-login-password.fxml"));
        Parent root = fxmlLoader.load();
        SetUpEmployeeController controller = fxmlLoader.getController();

        controller.setTempPracownik(selectedEmployee);
        Stage stage = new Stage();

        stage.setTitle("Przypisz login i hasło");
        stage.initModality(Modality.NONE);
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.show();
    }
}
