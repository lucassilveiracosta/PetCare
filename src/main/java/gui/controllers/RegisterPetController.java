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

    public static DomesticAnimal pendingAnimal;

    @FXML
    public void onNextButton(ActionEvent event) {
        try {
            String name = animalNameBox.getText();
            String specie = specieBox.getText();
            String race = raceBox.getText();
            LocalDate birthdate = birthDateBox.getValue();

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Pet name is required.");
            }
            if (birthdate == null) {
                throw new IllegalArgumentException("Please select a valid birth date.");
            }

            Temperament temperament = Temperament.valueOf(temperamentBox.getText().toUpperCase().trim());
            boolean castrated = Boolean.parseBoolean(castratedBox.getText().trim());
            StageOfLife stageOfLife = StageOfLife.valueOf(stageOfLifeBox.getText().toUpperCase().trim());
            Double weight = Double.parseDouble(weightBox.getText().trim());
            Size size = Size.valueOf(sizeBox.getText().toUpperCase().trim());
            Sex sex = Sex.valueOf(sexBox.getText().toUpperCase().trim());

            Owner owner = RegisterOwnerController.lastRegisteredOwner;

            if (owner == null) {
                throw new IllegalStateException("No owner found in memory. Did you skip the Owner registration screen?");
            }

            pendingAnimal = new DomesticAnimal(name, specie, race, birthdate, stageOfLife, weight, size, sex, owner, temperament, castrated, new ArrayList<>());

            Navigator.navigate("Pet Vaccines", "/view/fxml/RegisterPetVaccine.fxml");

        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", "Please check your inputs (e.g., must match exactly: MALE, SMALL, ADULT).\nDetails: " + e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            showAlert("System Error", "Unexpected error: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackButton(ActionEvent event) {
        Navigator.navigate("Select Owner", "/view/fxml/RegisterPetOwnerRegistred.fxml");
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}