package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterOwnerController {

    @FXML
    public void onRegisterClick(ActionEvent event){
        System.out.println("opa");
    }
    @FXML
    public void backButton(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/AttendantMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (IOException e) {
            System.out.println("Deu erro ao tentar abrir a tela anterior: " + e.getMessage());
            e.printStackTrace();
        }
        }
    }
