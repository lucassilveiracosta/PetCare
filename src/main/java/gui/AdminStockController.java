package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerStock;
import business.model.invoice.Product;
import enums.MedicineType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminStockController implements Initializable {

    @FXML private ToggleButton btnPetShop, btnVet;
    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, String> colName, colQty, colPrice, colType, colDescription;

    @FXML private TextField fieldName, fieldQuantity, fieldPrice;
    @FXML private TextArea fieldDescription;
    @FXML private CheckBox chkIsVet;
    @FXML private ChoiceBox<MedicineType> cbMedicineType;
    @FXML private Button btnAdd, btnSave, btnDelete;

    private IControllerStock stockCtrl;
    private ToggleGroup viewGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stockCtrl = ControllerPetCareServer.getInstance().getStock();

        viewGroup = new ToggleGroup();
        btnPetShop.setToggleGroup(viewGroup);
        btnVet.setToggleGroup(viewGroup);
        btnPetShop.setSelected(true);
        viewGroup.selectedToggleProperty().addListener((obs, old, nw) -> {
            if (nw == null) viewGroup.selectToggle(old);
            else refresh();
        });

        cbMedicineType.setItems(FXCollections.observableArrayList(MedicineType.values()));
        cbMedicineType.getSelectionModel().select(MedicineType.COMUM);
        cbMedicineType.disableProperty().bind(chkIsVet.selectedProperty().not());

        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colQty.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getQuantity())));
        colPrice.setCellValueFactory(c -> new SimpleStringProperty(money(c.getValue().getPrice())));
        colType.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getMedicineType() != null ? c.getValue().getMedicineType().name() : "-"));
        colDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));

        tableProducts.getSelectionModel().selectedItemProperty().addListener((obs, old, p) -> populateForm(p));

        refresh();
    }

    private boolean isVetView() {
        return viewGroup.getSelectedToggle() == btnVet;
    }

    private void refresh() {
        List<Product> items = isVetView() ? stockCtrl.filterVeterinarianProducts() : stockCtrl.filterPetShopProducts();
        tableProducts.setItems(FXCollections.observableArrayList(items));
        colType.setVisible(isVetView());
        handleClear();
    }

    private void populateForm(Product p) {
        if (p == null) { btnDelete.setDisable(true); btnSave.setDisable(true); return; }
        fieldName.setText(p.getName());
        fieldQuantity.setText(String.valueOf(p.getQuantity()));
        fieldPrice.setText(String.valueOf(p.getPrice()));
        fieldDescription.setText(p.getDescription());
        chkIsVet.setSelected(p.isVet());
        // isVet and medicine type are immutable after creation → lock while a product is selected
        chkIsVet.setDisable(true);
        if (p.getMedicineType() != null) cbMedicineType.getSelectionModel().select(p.getMedicineType());
        btnDelete.setDisable(false);
        btnSave.setDisable(false);
    }

    @FXML
    private void handleAdd() {
        try {
            boolean isVet = chkIsVet.isSelected();
            MedicineType type = isVet ? cbMedicineType.getValue() : null;
            Product p = new Product(
                    fieldName.getText(),
                    parseInt(fieldQuantity.getText()),
                    blankToDash(fieldDescription.getText()),
                    parseDouble(fieldPrice.getText()),
                    isVet, type);
            stockCtrl.post(p);
            refresh();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Could not add product", ex.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        Product p = tableProducts.getSelectionModel().getSelectedItem();
        if (p == null) return;
        try {
            // name/qty/price/description are mutable; isVet and medicineType are not (model constraint)
            p.setName(fieldName.getText());
            p.setQuantity(parseInt(fieldQuantity.getText()));
            p.setPrice(parseDouble(fieldPrice.getText()));
            p.setDescription(blankToDash(fieldDescription.getText()));
            tableProducts.refresh();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Could not save changes", ex.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Product p = tableProducts.getSelectionModel().getSelectedItem();
        if (p == null) return;
        try {
            stockCtrl.delete(p.getId());
            refresh();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Could not delete", ex.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        tableProducts.getSelectionModel().clearSelection();
        fieldName.clear();
        fieldQuantity.clear();
        fieldPrice.clear();
        fieldDescription.clear();
        chkIsVet.setSelected(isVetView());
        chkIsVet.setDisable(false);
        cbMedicineType.getSelectionModel().select(MedicineType.COMUM);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
    }

    @FXML
    private void handleRefresh() {
        refresh();
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

    private static String money(Double v) {
        return v != null ? String.format("R$ %.2f", v) : "-";
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
