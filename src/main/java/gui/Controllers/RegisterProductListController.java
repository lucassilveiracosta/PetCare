package gui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterProductListController {
    @FXML
    public void openAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterProductPopup.fxml")); // carrega o popup
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Adicionar Novo Produto");

            popupStage.initModality(Modality.APPLICATION_MODAL);

            Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            popupStage.initOwner(mainWindow);

            popupStage.showAndWait();

        } catch (IOException e) {
            System.out.println("Erro ao tentar abrir o popup: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void backbutton(ActionEvent event) {
        try {
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
