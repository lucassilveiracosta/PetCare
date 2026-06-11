package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerStock;
import business.model.invoice.Product;
import enums.MedicineType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterProductController implements Initializable {

    @FXML private TextField fieldName;
    @FXML private TextField fieldQuantity;
    @FXML private TextField fieldPrice;
    @FXML private TextField fieldDescription;
    @FXML private CheckBox chkIsVet;
    @FXML private ChoiceBox<MedicineType> cbMedicineType;

    private final IControllerStock stockController =
            ControllerPetCareServer.getInstance().getStock();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbMedicineType.getItems().setAll(MedicineType.values());
        cbMedicineType.getSelectionModel().select(MedicineType.COMUM);
        // Medicine type only applies to veterinary products
        cbMedicineType.disableProperty().bind(chkIsVet.selectedProperty().not());
    }

    @FXML
    public void onRegister(ActionEvent event) {
        try {
            boolean isVet = chkIsVet.isSelected();
            MedicineType type = isVet ? cbMedicineType.getValue() : null;

            Product product = new Product(
                    fieldName.getText(),
                    parseInt(fieldQuantity.getText()),
                    blankToDash(fieldDescription.getText()),
                    parseDouble(fieldPrice.getText()),
                    isVet,
                    type);

            stockController.post(product);

            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Product \"" + product.getName() + "\" registered.");
            close(event);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid number",
                    "Quantity and price must be valid numbers.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not register", e.getMessage());
        }
    }

    @FXML
    public void backButton(ActionEvent event) {
        close(event);
    }

    private void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private static String blankToDash(String s) {
        return (s != null && !s.isBlank()) ? s.trim() : "-";
    }

    private static Integer parseInt(String s) {
        if (s == null || s.isBlank()) return 0;
        return Integer.valueOf(s.trim());
    }

    private static Double parseDouble(String s) {
        if (s == null || s.isBlank()) return 0.0;
        return Double.valueOf(s.trim().replace(',', '.'));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
