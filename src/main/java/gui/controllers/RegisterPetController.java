package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.person.Owner;
import enums.Sex;
import enums.Size;
import enums.StageOfLife;
import enums.Temperament;
import gui.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterPetController {

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
            
            if (birthdate == null) {
                throw new IllegalArgumentException("Por favor, selecione uma data de nascimento válida.");
            }

            // Convertendo textos para Enums
            Temperament temperament = Temperament.valueOf(temperamentBox.getText().toUpperCase());
            boolean castrated = Boolean.parseBoolean(castratedBox.getText());
            StageOfLife stageOfLife = StageOfLife.valueOf(stageOfLifeBox.getText().toUpperCase());
            Double weight = Double.parseDouble(weightBox.getText());
            Size size = Size.valueOf(sizeBox.getText().toUpperCase());
            Sex sex = Sex.valueOf(sexBox.getText().toUpperCase());
            
            Owner owner = RegisterOwnerController.lastRegisteredOwner;
            
            if (owner == null) {
                throw new IllegalArgumentException("Nenhum dono foi encontrado na memória. Você pulou a tela de cadastro do dono?");
            }

            pendingAnimal = new DomesticAnimal(name, specie, race, birthdate, stageOfLife, weight, size, sex, owner, temperament, castrated, new ArrayList<>());

            Navigator.navigate("Pet Vaccines", "/view/fxml/RegisterPetVaccine.fxml");
            
        } catch (Exception e) {
            showAlert("Erro", "Verifique se preencheu os campos corretamente (ex: MACHO, PEQUENO, ADULTO). Erro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String erro, String s, Alert.AlertType alertType) {}


    @FXML
    public void onBackButton(ActionEvent event) {
        gui.Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }

}
