package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPetQuestionController {
    @FXML
    public void onYesButton(ActionEvent event) {
        gui.Navigator.navigate("Select Owner", "/view/fxml/RegisterPetOwnerRegistred.fxml");
    }

    @FXML
    public void onNoButton(ActionEvent event) {
        gui.Navigator.navigate("Pet Owner", "/view/fxml/RegisterPetOwner.fxml");
    }
}
