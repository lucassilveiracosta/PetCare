package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAnimal;
import business.model.animal.Vaccine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class RegisterPetVaccineController {

    @FXML private TextField txtVaccineName;
    @FXML private DatePicker dpVaccineDate;
    @FXML private TextField txtDescription;
    @FXML private DatePicker dpExpireDate;
    @FXML private ToggleButton btnIsRabbies;

    private boolean isRabbies = false;

    private final IControllerAnimal backendController = ControllerPetCareServer.getInstance().getAnimal();

    @FXML
    public void onIsRabbiesToggle(ActionEvent event) {
        isRabbies = !isRabbies;
        if (isRabbies) {
            btnIsRabbies.setText("Yes");
            btnIsRabbies.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        } else {
            btnIsRabbies.setText("No");
            btnIsRabbies.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        }
    }

    @FXML
    public void onAddMoreClick(ActionEvent event) {
        try {
            String name = txtVaccineName.getText();
            LocalDate date = dpVaccineDate.getValue();
            String description = txtDescription.getText();
            LocalDate expireDate = dpExpireDate.getValue();

            if (name == null || name.trim().isEmpty() || date == null) {
                throw new IllegalArgumentException("Nome e Data da vacina são obrigatórios.");
            }

            Vaccine newVaccine = new Vaccine(name, date, description, isRabbies, expireDate);


            showAlert("Sucesso!", "Vacina adicionada. Pode inserir a próxima.", Alert.AlertType.INFORMATION);
            clearFields();

        } catch (IllegalArgumentException e) {
            showAlert("Erro de Validação", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            showAlert("Erro no Sistema", "Não foi possível adicionar a vacina: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onRegisterClick(ActionEvent event) {

        try {

            showAlert("Sucesso!", "Registro do Animal e Vacinas concluído!", Alert.AlertType.INFORMATION);

            gui.Navigator.navigate("Attendant Menu", "/view/fxml/AttendantMenu.fxml");

        } catch (Exception e) {
            showAlert("Erro no Sistema", "Falha ao registrar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        if(txtVaccineName != null) txtVaccineName.clear();
        if(txtDescription != null) txtDescription.clear();
        if(dpVaccineDate != null) dpVaccineDate.setValue(null);
        if(dpExpireDate != null) dpExpireDate.setValue(null);

        isRabbies = false;
        if(btnIsRabbies != null) {
            btnIsRabbies.setText("No");
            btnIsRabbies.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        }
    }
}