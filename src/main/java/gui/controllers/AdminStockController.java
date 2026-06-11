package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerExpense;
import business.interfaces.IControllerStock;
import business.model.invoice.Expense;
import business.model.invoice.Product;
import business.report.PdfReportService;
import enums.ExpenseType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Admin stock management: pick a product (radio), increase/decrease its quantity
 * with the −/+ counter, add new products (same form the attendant uses), see the
 * total stock value and generate a stock finance report.
 */
public class AdminStockController implements Initializable {

    @FXML private ToggleButton btnPetShop, btnVet;
    @FXML private VBox productRadios;
    @FXML private Label lblSelectedName, lblCount, lblTotalValue;

    private IControllerStock stockCtrl;
    private IControllerExpense expenseCtrl;
    private ToggleGroup viewGroup;
    private ToggleGroup productGroup;
    private Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stockCtrl = ControllerPetCareServer.getInstance().getStock();
        expenseCtrl = ControllerPetCareServer.getInstance().getExpense();

        viewGroup = new ToggleGroup();
        btnPetShop.setToggleGroup(viewGroup);
        btnVet.setToggleGroup(viewGroup);
        btnPetShop.setSelected(true);
        viewGroup.selectedToggleProperty().addListener((obs, old, nw) -> {
            if (nw == null) viewGroup.selectToggle(old);
            else { selectedProduct = null; refresh(); }
        });

        refresh();
    }

    private boolean isVetView() {
        return viewGroup.getSelectedToggle() == btnVet;
    }

    private void refresh() {
        List<Product> items = isVetView() ? stockCtrl.filterVeterinarianProducts() : stockCtrl.filterPetShopProducts();

        productGroup = new ToggleGroup();
        productRadios.getChildren().clear();
        for (Product p : items) {
            double price = p.getPrice() != null ? p.getPrice() : 0.0;
            RadioButton rb = new RadioButton(String.format("%s   (qty: %d)   R$ %.2f", p.getName(), p.getQuantity(), price));
            rb.setToggleGroup(productGroup);
            rb.setUserData(p);
            productRadios.getChildren().add(rb);
            if (p == selectedProduct) rb.setSelected(true);
        }
        productGroup.selectedToggleProperty().addListener((o, ov, nv) -> {
            selectedProduct = nv != null ? (Product) nv.getUserData() : null;
            updateCounter();
        });

        double total = 0.0;
        for (Product p : stockCtrl.getAll()) total += value(p);
        lblTotalValue.setText(money(total));

        updateCounter();
    }

    private void updateCounter() {
        if (selectedProduct != null) {
            lblSelectedName.setText(selectedProduct.getName());
            lblCount.setText(String.valueOf(selectedProduct.getQuantity()));
        } else {
            lblSelectedName.setText("—");
            lblCount.setText("0");
        }
    }

    @FXML
    private void handleIncrease() {
        if (selectedProduct == null) return;
        selectedProduct.setQuantity(selectedProduct.getQuantity() + 1);
        ControllerPetCareServer.getInstance().saveAll();
        refresh();
    }

    @FXML
    private void handleDecrease() {
        if (selectedProduct == null || selectedProduct.getQuantity() <= 0) return;
        selectedProduct.setQuantity(selectedProduct.getQuantity() - 1);
        ControllerPetCareServer.getInstance().saveAll();
        refresh();
    }

    @FXML
    private void handleAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/RegisterProduct.fxml"));
            Parent root = loader.load();
            Stage popup = new Stage();
            popup.setScene(new Scene(root));
            popup.setTitle("Register Product");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(productRadios.getScene().getWindow());
            popup.showAndWait();
            refresh();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not open form", e.getMessage());
        }
    }

    @FXML
    private void handleReport() {
        double spentThisMonth = stockSpentThisMonth();
        double stockValue = 0.0;
        for (Product p : stockCtrl.getAll()) stockValue += value(p);

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Stock Report (PDF)");
        chooser.setInitialFileName("StockReport.pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(productRadios.getScene().getWindow());
        if (dest == null) return;

        try {
            new PdfReportService().generateStockFinanceReport(stockCtrl.getAll(), spentThisMonth, stockValue, dest);
            showAlert(Alert.AlertType.INFORMATION, "Report generated",
                    String.format("Spent on stock this month: %s%nValue still in stock: %s%n%nPDF saved to:%n%s",
                            money(spentThisMonth), money(stockValue), dest.getAbsolutePath()));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not generate report", e.getMessage());
        }
    }

    private double stockSpentThisMonth() {
        LocalDate now = LocalDate.now();
        double total = 0.0;
        for (Expense e : expenseCtrl.filterByType(ExpenseType.INVENTORY)) {
            if (e.getDate() != null
                    && e.getDate().getMonth() == now.getMonth()
                    && e.getDate().getYear() == now.getYear()) {
                total += e.getAmount() != null ? e.getAmount() : 0.0;
            }
        }
        return total;
    }

    private static double value(Product p) {
        double price = p.getPrice() != null ? p.getPrice() : 0.0;
        return price * p.getQuantity();
    }

    @FXML
    private void handleRefresh() {
        refresh();
    }

    private static String money(double v) {
        return String.format("R$ %.2f", v);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
