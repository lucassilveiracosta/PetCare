package gui.controllers;

import business.controller.ControllerAnimal;
import business.controller.ControllerPetCareServer;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import exceptions.InvalidAnimalAgeException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class RegisterPetVaccineController {

    @FXML private TextField txtVaccineName;
    @FXML private DatePicker dpVaccineDate;
    @FXML private TextField txtDescription;
    @FXML private DatePicker dpExpireDate;
    @FXML private ToggleButton btnIsRabies;

    private boolean isRabies = false;

    @FXML
    public void onIsRabiesToggle(ActionEvent event) {
        isRabies = !isRabies;
        if (isRabies) {
            btnIsRabies.setText("Yes");
            btnIsRabies.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        } else {
            btnIsRabies.setText("No");
            btnIsRabies.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        }
    }

    @FXML
    public void onAddMoreClick(ActionEvent event) {
        try {
            DomesticAnimal pet = RegisterPetController.pendingAnimal;
            if (pet == null) {
                throw new IllegalStateException("No pet in registration. Go back and fill in the pet details first.");
            }

            String name = txtVaccineName.getText();
            LocalDate date = dpVaccineDate.getValue();
            String description = txtDescription.getText();
            LocalDate expireDate = dpExpireDate.getValue();

            if (name == null || name.trim().isEmpty() || date == null) {
                throw new IllegalArgumentException("Vaccine name and date are required.");
            }
            if (expireDate == null) {
                throw new IllegalArgumentException("The vaccine expiration date is required.");
            }
            if (expireDate.isBefore(date)) {
                throw new IllegalArgumentException("The expiration date must be after the vaccine date.");
            }
            ControllerAnimal.validateVaccinationAge(pet.getBirthDate(), date, isRabies); // REQ19

            String desc = (description == null || description.isBlank()) ? "-" : description.trim();
            Vaccine newVaccine = new Vaccine(name.trim(), date, desc, isRabies, expireDate);
            pet.getVaccines().add(newVaccine);

            showAlert("Success!", "Vaccine added (" + pet.getVaccines().size()
                    + " on the card). You can add the next one.", Alert.AlertType.INFORMATION);
            clearFields();

        } catch (IllegalArgumentException | IllegalStateException | InvalidAnimalAgeException e) {
            showAlert("Validation Error", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            showAlert("System Error", "Could not add the vaccine: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onRegisterClick(ActionEvent event) {
        try {
            DomesticAnimal pet = RegisterPetController.pendingAnimal;
            if (pet == null) {
                throw new IllegalStateException("No pet in registration. Go back and fill in the pet details first.");
            }

            ControllerPetCareServer.getInstance().getAnimal().post(pet);
            ControllerPetCareServer.getInstance().saveAll();

            int vaccineCount = pet.getVaccines().size();
            RegisterPetController.pendingAnimal = null;

            showAlert("Success!", "Registration of pet '" + pet.getName() + "' completed with "
                    + vaccineCount + " vaccine(s).", Alert.AlertType.INFORMATION);

            gui.Navigator.navigate("Attendant Menu", "/view/fxml/AttendantMenu.fxml");

        } catch (IllegalStateException e) {
            showAlert("Validation Error", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            showAlert("System Error", "Falha ao registrar: " + e.getMessage(), Alert.AlertType.ERROR);
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

        isRabies = false;
        if(btnIsRabies != null) {
            btnIsRabies.setText("No");
            btnIsRabies.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        }
    }
}