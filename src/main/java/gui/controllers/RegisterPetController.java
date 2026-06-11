package gui.controllers;

import business.model.animal.DomesticAnimal;
import business.model.person.Owner;
import enums.Sex;
import enums.Size;
import enums.StageOfLife;
import enums.Temperament;
import gui.Navigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterPetController {

    // 1. FXML Elements
    @FXML private TextField animalNameBox;
    @FXML private TextField specieBox;
    @FXML private TextField raceBox;
    @FXML private TextField temperamentBox;
    @FXML private DatePicker birthDateBox;
    @FXML private TextField castratedBox;
    @FXML private TextField stageOfLifeBox;
    @FXML private TextField weightBox;
    @FXML private TextField sizeBox;
    @FXML private TextField sexBox;

    // A memória estática que vai guardar o animal para a tela de vacinas usar!
    public static DomesticAnimal pendingAnimal;

    @FXML
    public void onNextButton(ActionEvent event) {
        try {
            // 1. Capture basic inputs
            String name = animalNameBox.getText();
            String specie = specieBox.getText();
            String race = raceBox.getText();
            LocalDate birthdate = birthDateBox.getValue();

            // Basic validation
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Pet name is required.");
            }
            if (birthdate == null) {
                throw new IllegalArgumentException("Please select a valid birth date.");
            }

            // 2. Convert text inputs to Enums/Numbers (using .trim() to avoid invisible space errors)
            Temperament temperament = Temperament.valueOf(temperamentBox.getText().toUpperCase().trim());
            boolean castrated = Boolean.parseBoolean(castratedBox.getText().trim());
            StageOfLife stageOfLife = StageOfLife.valueOf(stageOfLifeBox.getText().toUpperCase().trim());
            Double weight = Double.parseDouble(weightBox.getText().trim());
            Size size = Size.valueOf(sizeBox.getText().toUpperCase().trim());
            Sex sex = Sex.valueOf(sexBox.getText().toUpperCase().trim());

            // 3. Get the owner from the previous screen's static variable
            // (Make sure this variable is public static in your RegisterOwnerController!)
            Owner owner = RegisterOwnerController.lastRegisteredOwner;

            if (owner == null) {
                throw new IllegalStateException("No owner found in memory. Did you skip the Owner registration screen?");
            }

            // 4. Instantiate and save to the static variable
            pendingAnimal = new DomesticAnimal(name, specie, race, birthdate, stageOfLife, weight, size, sex, owner, temperament, castrated, new ArrayList<>());

            // 5. Navigate to the next step
            Navigator.navigate("Pet Vaccines", "/view/fxml/RegisterPetVaccine.fxml");

        } catch (IllegalArgumentException e) {
            // Catches Enum parsing errors (e.g., user typed "Big" instead of "LARGE")
            showAlert("Validation Error", "Please check your inputs (e.g., must match exactly: MALE, SMALL, ADULT).\nDetails: " + e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            // Catches unexpected system errors
            showAlert("System Error", "Unexpected error: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackButton(ActionEvent event) {
        // Adapt back navigation to whatever the previous screen was (e.g., the owner selection screen)
        Navigator.navigate("Select Owner", "/view/fxml/RegisterPetOwnerRegistred.fxml");
    }

    // Standardized Helper Method (Now fully functional!)
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}