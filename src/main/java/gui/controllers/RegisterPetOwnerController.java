package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerPerson;
import business.model.person.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class RegisterPetOwnerController {

    // 1. Elementos da Tela
    @FXML private TextField txtOwnerName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private DatePicker dpBirthDate;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtJob;
    @FXML private TextField txtDescription;

    // 2. Conexão com o Servidor
    private final IControllerPerson backendController = ControllerPetCareServer.getInstance().getPessoa();

    @FXML
    public void onNextButton(ActionEvent event) {
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
                throw new IllegalArgumentException("Please select a valid birth date.");
            }

            Owner newOwner = new Owner(name, email, password, birthdate, cpf, telephone, job, description);
            backendController.post(newOwner);

            showAlert("Success!", "Owner registered successfully. Proceeding to pet details.", Alert.AlertType.INFORMATION);

            // NAVEGAÇÃO: Só vai para a próxima tela se não der nenhum erro no try!
            gui.Navigator.navigate("Pet Details", "/view/fxml/RegisterPet.fxml");

        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            showAlert("System Error", "Could not save the registration: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackButton(ActionEvent event) {
        // Volta para a pergunta inicial
        gui.Navigator.navigate("Register Pet", "/view/fxml/RegisterPetQuestion.fxml");
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}