package gui.controllers;

import business.controller.ControllerPerson;
import business.interfaces.IControllerPerson;
import business.model.person.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterOwnerController {

    @FXML private TextField txtOwnerName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private DatePicker dpBirthDate;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtJob;
    @FXML private TextField txtDescription;

    private final IControllerPerson backendController = business.controller.ControllerPetCareServer.getInstance().getPessoa();

    @FXML
    public void onRegisterClick(ActionEvent event) {
        String name = txtOwnerName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        LocalDate birthdate = dpBirthDate.getValue();
        String cpf = txtCpf.getText();
        String telephone = txtTelephone.getText();
        String job = txtJob.getText();
        String description = txtDescription.getText();

        try {
            if (birthdate == null) {
                throw new IllegalArgumentException("Por favor, selecione uma data de nascimento válida.");
            }

            Owner newOwner = new Owner(name, email, password, birthdate, cpf, telephone, job, description);
            backendController.post(newOwner);

            showAlert("Sucesso!", "Dono cadastrado perfeitamente.", Alert.AlertType.INFORMATION);
            clearFields();

        } catch (IllegalArgumentException e) {
            showAlert("Erro de Validação", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            showAlert("Erro no Sistema", "Não foi possível salvar o cadastro: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        gui.Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        if(txtOwnerName != null) txtOwnerName.clear();
        if(txtEmail != null) txtEmail.clear();
        if(txtPassword != null) txtPassword.clear();
        if(dpBirthDate != null) dpBirthDate.setValue(null);
        if(txtCpf != null) txtCpf.clear();
        if(txtTelephone != null) txtTelephone.clear();
        if(txtJob != null) txtJob.clear();
        if(txtDescription != null) txtDescription.clear();
    }
}