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
    public void onYesButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterPetOwnerRegistred.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Register Pet Owner Registred");
        stage.show();

    }
    @FXML
    public void onNoButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/RegisterPetOwner.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register Pet Owner");
            stage.show();

        } catch (IOException e) {
            System.out.println("Deu erro ao tentar abrir a tela anterior: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
