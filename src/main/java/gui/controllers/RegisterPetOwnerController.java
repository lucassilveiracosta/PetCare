package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPetOwnerController {

    @FXML
    public void onNextButton(ActionEvent event) {
        gui.Navigator.navigate("Pet Details", "/view/fxml/RegisterPet.fxml");
    }

    @FXML
    public void onBackButton(ActionEvent event) {
        gui.Navigator.navigate("Register Pet", "/view/fxml/RegisterPetQuestion.fxml");
    }
    }
