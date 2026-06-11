package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.person.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterPetOwnerRegistredController {

    @FXML
    private MenuButton OwnerName;

    private Owner selectedOwner = null;

    @FXML
    public void initialize() {
        loadOwnersIntoMenu();
    }

    private void loadOwnersIntoMenu() {
        OwnerName.getItems().clear();

        ArrayList<Owner> owners = ControllerPetCareServer.getInstance().getPerson().getAllOwners();

        if (owners == null || owners.isEmpty()) {
            MenuItem emptyItem = new MenuItem("No owner found");
            OwnerName.getItems().add(emptyItem);
            return;
        }

        for (Owner owner : owners) {
            MenuItem optionItem = new MenuItem(owner.getName() + " (CPF: " + owner.getCpf() + ")");

            optionItem.setOnAction(event -> {
                OwnerName.setText(owner.getName());
                selectedOwner = owner;

                System.out.println("User selected owner: " + selectedOwner.getName());
            });

            OwnerName.getItems().add(optionItem);
        }
    }

    @FXML
    public void onNextButton(ActionEvent event) {
        if (selectedOwner == null) {
            showAlert("Validation Error", "Select an owner before continuing.", Alert.AlertType.WARNING);
            return;
        }
        // Hand the selected owner to the pet screen (read in RegisterPetController).
        RegisterOwnerController.lastRegisteredOwner = selectedOwner;
        gui.Navigator.navigate("Pet Details", "/view/fxml/RegisterPet.fxml");
    }

    @FXML
    public void onBackButton(ActionEvent event) {
        gui.Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}