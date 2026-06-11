package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.invoice.Product;
import business.report.PdfReportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class RegisterProductPopupController {

    @FXML
    public void onYesPdf(ActionEvent event) {
        List<Product> products = ControllerPetCareServer.getInstance().getStock().getAll();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Receipt (PDF)");
        chooser.setInitialFileName("PetShopReceipt.pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (dest == null) return;

        try {
            new PdfReportService().generateStockReport(products, dest);
            showAlert(Alert.AlertType.INFORMATION, "Receipt generated", "PDF saved to:\n" + dest.getAbsolutePath());
            close(event);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not generate PDF", e.getMessage());
        }
    }

    @FXML
    public void onNoPdf(ActionEvent event) {
        close(event);
    }

    private void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
