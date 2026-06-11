package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerStock;
import business.model.invoice.Product;
import gui.Navigator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterProductListController implements Initializable {

    @FXML private ListView<String> listproduct;
    @FXML private Text valuetotal;

    private final IControllerStock stockController =
            ControllerPetCareServer.getInstance().getStock();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = stockController.getAll();
        var items = FXCollections.<String>observableArrayList();
        double total = 0.0;
        for (Product p : products) {
            double price = p.getPrice() != null ? p.getPrice() : 0.0;
            total += price * p.getQuantity();
            String type = p.isVet()
                    ? (p.getMedicineType() != null ? p.getMedicineType().name() : "VET")
                    : "PET SHOP";
            items.add(String.format("%-22s  qty: %-4d  R$ %8.2f   [%s]",
                    p.getName(), p.getQuantity(), price, type));
        }
        if (items.isEmpty()) items.add("No products registered.");
        listproduct.setItems(items);
        valuetotal.setText(String.format("R$ %.2f", total));
    }

    @FXML
    public void openAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterProduct.fxml"));
            Parent root = loader.load();

            Stage popup = new Stage();
            popup.setScene(new Scene(root));
            popup.setTitle("Register Product");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            popup.showAndWait();

            loadProducts(); // refresh after the popup closes
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not open form", e.getMessage());
        }
    }

    @FXML
    public void onPayment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterProductPopup.fxml"));
            Parent root = loader.load();

            Stage popup = new Stage();
            popup.setScene(new Scene(root));
            popup.setTitle("Payment");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            popup.showAndWait();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not open payment", e.getMessage());
        }
    }

    @FXML
    public void backbutton(ActionEvent event) {
        Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
