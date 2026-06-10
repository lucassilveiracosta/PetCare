package gui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;


public class AttendantMenuController {

    @FXML
    public void openPetShop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/RegisterProductList.fxml"));
        SplitPane root = new SplitPane();
        loader.setRoot(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.setTitle("Scheduling");
        stage.show();
    }

    @FXML
    public void openRegisterOwner(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/RegisterOwner.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.setTitle("Register Owner");
        stage.show();
        } catch (Exception e) {
            System.out.println("RegisterOwner didn't open");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openRegisterPet(ActionEvent event) {
        System.out.println("Abrindo tela de Register Pet...");
    }

    @FXML
    public void openPetShopScheduling(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/Scheduling.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (IOException e) {
            System.out.println("Deu erro ao tentar abrir a tela anterior: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void openVeterinarianScheduling(ActionEvent event) {

        System.out.println("Abrindo tela de Veterinarian Scheduling...");
    }
    @FXML
        public void backButton(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/Menu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


                stage.setScene(new Scene(root, 800, 600));
                stage.show();

            } catch (IOException e) {
                System.out.println("Deu erro ao tentar abrir a tela anterior: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

