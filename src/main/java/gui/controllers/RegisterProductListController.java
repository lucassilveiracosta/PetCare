package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerStock;
import business.model.invoice.Product;
import business.report.PdfReportService;
import enums.MedicineType;
import exceptions.InsufficientStockException;
import exceptions.PrescriptionRequiredException;
import gui.Navigator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterProductListController implements Initializable {

    @FXML private ListView<String> listproduct;
    @FXML private ListView<String> cartList;
    @FXML private Label valuetotal;

    private final IControllerStock stockController =
            ControllerPetCareServer.getInstance().getStock();

    private final List<Product> displayed = new ArrayList<>();
    private final Map<Product, Integer> cart = new LinkedHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProducts();
        listproduct.setOnMouseClicked(e -> addSelectedToCart());
        renderCart();
    }

    private void loadProducts() {
        displayed.clear();
        displayed.addAll(stockController.getAll());
        var items = FXCollections.<String>observableArrayList();
        for (Product p : displayed) {
            double price = p.getPrice() != null ? p.getPrice() : 0.0;
            String type = p.isVet()
                    ? (p.getMedicineType() != null ? p.getMedicineType().name() : "VET")
                    : "PET SHOP";
            items.add(String.format("%-22s  stock: %-4d  R$ %8.2f   [%s]",
                    p.getName(), p.getQuantity(), price, type));
        }
        if (items.isEmpty()) items.add("No products registered.");
        listproduct.setItems(items);
    }

    private void addSelectedToCart() {
        int idx = listproduct.getSelectionModel().getSelectedIndex();
        if (idx < 0 || idx >= displayed.size()) return;
        Product p = displayed.get(idx);

        int inCart = cart.getOrDefault(p, 0);
        // REQ20 — cannot bill more units than there are in stock
        if (inCart >= p.getQuantity()) {
            showAlert(Alert.AlertType.WARNING, "Out of stock",
                    "No more units of " + p.getName() + " available (stock: " + p.getQuantity() + ").");
            return;
        }
        cart.put(p, inCart + 1);
        renderCart();
    }

    @FXML
    public void removeFromCart(ActionEvent event) {
        int idx = cartList.getSelectionModel().getSelectedIndex();
        List<Product> keys = new ArrayList<>(cart.keySet());
        if (idx < 0 || idx >= keys.size()) return;
        Product p = keys.get(idx);
        int qty = cart.get(p) - 1;
        if (qty <= 0) cart.remove(p);
        else cart.put(p, qty);
        renderCart();
    }

    @FXML
    public void clearCart(ActionEvent event) {
        cart.clear();
        renderCart();
    }

    private void renderCart() {
        var items = FXCollections.<String>observableArrayList();
        for (Map.Entry<Product, Integer> e : cart.entrySet()) {
            Product p = e.getKey();
            double price = p.getPrice() != null ? p.getPrice() : 0.0;
            items.add(String.format("%-20s  x%-3d  R$ %8.2f", p.getName(), e.getValue(), price * e.getValue()));
        }
        if (items.isEmpty()) items.add("Cart is empty.");
        cartList.setItems(items);
        valuetotal.setText(String.format("R$ %.2f", cartTotal()));
    }

    private double cartTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> e : cart.entrySet()) {
            double price = e.getKey().getPrice() != null ? e.getKey().getPrice() : 0.0;
            total += price * e.getValue();
        }
        return total;
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
            loadProducts();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not open form", e.getMessage());
        }
    }

    @FXML
    public void onPayment(ActionEvent event) {
        if (cart.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Empty cart", "Add products to the cart before payment.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Confirm payment of " + String.format("R$ %.2f", cartTotal()) + "?",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText(null);
        confirm.setTitle("Payment");
        if (confirm.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        // REQ15 — a cart with controlled medicine requires a prescription
        boolean hasControlled = cart.keySet().stream()
                .anyMatch(p -> p.isVet() && p.getMedicineType() == MedicineType.CONTROLADO);
        boolean hasPrescription = false;
        if (hasControlled) {
            Alert presc = new Alert(Alert.AlertType.CONFIRMATION,
                    "O carrinho contém medicamento controlado. O cliente apresentou a receita?",
                    ButtonType.YES, ButtonType.NO);
            presc.setHeaderText(null);
            presc.setTitle("Receita");
            hasPrescription = presc.showAndWait().filter(b -> b == ButtonType.YES).isPresent();
            if (!hasPrescription) {
                showAlert(Alert.AlertType.WARNING, "Venda bloqueada",
                        "Há medicamento controlado no carrinho e nenhuma receita foi apresentada.");
                return;
            }
        }

        // Register each sale through the controller (enforces REQ15 + REQ20)
        try {
            for (Map.Entry<Product, Integer> e : cart.entrySet()) {
                stockController.registerSale(e.getKey(), e.getValue(), hasPrescription);
            }
        } catch (InsufficientStockException | PrescriptionRequiredException ex) {
            showAlert(Alert.AlertType.WARNING, "Venda bloqueada", ex.getMessage());
            loadProducts();
            return; // keep the cart so the attendant can adjust
        }
        ControllerPetCareServer.getInstance().saveAll(); // persist the stock change

        // Optional receipt PDF
        Alert receipt = new Alert(Alert.AlertType.CONFIRMATION, "Generate receipt in PDF format?",
                ButtonType.YES, ButtonType.NO);
        receipt.setHeaderText(null);
        receipt.setTitle("Receipt");
        Optional<ButtonType> r = receipt.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.YES) {
            generateReceipt(event);
        }

        cart.clear();
        loadProducts();
        renderCart();
    }

    private void generateReceipt(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Receipt (PDF)");
        chooser.setInitialFileName("PetShopReceipt.pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (dest == null) return;
        try {
            new PdfReportService().generateSaleReceipt(cart, cartTotal(), dest);
            showAlert(Alert.AlertType.INFORMATION, "Receipt generated", "PDF saved to:\n" + dest.getAbsolutePath());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not generate PDF", e.getMessage());
        }
    }

    @FXML
    public void backbutton(ActionEvent event) {
        if (!cart.isEmpty()) {
            ButtonType voltar = new ButtonType("Voltar");
            ButtonType continuar = new ButtonType("Continuar pagamento");
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Há uma venda em andamento. Deseja sair e cancelar, ou continuar o pagamento?",
                    voltar, continuar);
            confirm.setHeaderText(null);
            confirm.setTitle("Confirmação");
            Optional<ButtonType> choice = confirm.showAndWait();
            if (choice.isEmpty() || choice.get() != voltar) return; // continue payment / stay
            cart.clear();
            renderCart();
        }
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
