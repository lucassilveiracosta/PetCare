package gui.controllers;

import gui.MockDataLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterProductPopupController {


    @FXML
    public void onYesButton(ActionEvent event) throws IOException{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterPetOwnerRegistred.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("AttendantMenu");
            stage.show();
    }

    public void onNoButton(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterPetOwner.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("AttendantMenu");
        stage.show();
    }


}
