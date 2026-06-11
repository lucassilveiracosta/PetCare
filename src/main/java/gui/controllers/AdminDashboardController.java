package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.*;
import business.model.invoice.*;
import business.model.appointment.Appointment;
import business.report.PdfReportService;
import enums.AppointmentStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminDashboardController implements Initializable {

    // System Overview
    @FXML private FlowPane systemCards;

    // Financial
    @FXML private Label lblTotalIn, lblTotalOut, lblBalance;
    @FXML private DatePicker dpFrom, dpTo;
    @FXML private ChoiceBox<String> cbCategory;
    @FXML private TableView<FinanceRow> tableTransactions;
    @FXML private TableColumn<FinanceRow, String> colTxDate, colTxDir, colTxCategory, colTxAmount;
    @FXML private TableView<Invoice> tableInvoices;
    @FXML private TableColumn<Invoice, String> colInvId, colInvOwner, colInvAnimal, colInvDate, colInvTotal;
    @FXML private Button btnExportInvoice;

    private IControllerPerson personCtrl;
    private IControllerAnimal animalCtrl;
    private IControllerAppointment appointmentCtrl;
    private IControllerStock stockCtrl;
    private IControllerInvoice invoiceCtrl;
    private IControllerExpense expenseCtrl;

    private final List<FinanceRow> allRows = new ArrayList<>();

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControllerPetCareServer server = ControllerPetCareServer.getInstance();
        personCtrl = server.getPerson();
        animalCtrl = server.getAnimal();
        appointmentCtrl = server.getAppointment();
        stockCtrl = server.getStock();
        invoiceCtrl = server.getInvoice();
        expenseCtrl = server.getExpense();

        buildSystemCards();
        buildFinanceRows();
        setupTransactionTable();
        setupInvoiceTable();
        setupCategoryFilter();
        applyFilter();
    }

    // ── System Overview ───────────────────────────────────────────────────────
    private void buildSystemCards() {
        List<Appointment> appts = appointmentCtrl.getAll();
        long pending = countStatus(appts, AppointmentStatus.PENDING);
        long inProgress = countStatus(appts, AppointmentStatus.IN_PROGRESS);
        long expired = countStatus(appts, AppointmentStatus.EXPIRED);
        long completed = countStatus(appts, AppointmentStatus.COMPLETED);
        long lowStock = stockCtrl.getAll().stream().filter(p -> p.getQuantity() < 5).count();

        systemCards.getChildren().addAll(
                card("Veterinarians", personCtrl.getAllVets().size()),
                card("Employees", personCtrl.getAllEmployees().size()),
                card("Owners", personCtrl.getAllOwners().size()),
                card("Animals", animalCtrl.getAll().size()),
                card("Appointments (total)", appts.size()),
                card("· Pending", (int) pending),
                card("· In Progress", (int) inProgress),
                card("· Expired", (int) expired),
                card("· Completed", (int) completed),
                card("Pet Shop Products", stockCtrl.filterPetShopProducts().size()),
                card("Vet Products", stockCtrl.filterVeterinarianProducts().size()),
                card("Low Stock (<5)", (int) lowStock),
                card("Invoices", invoiceCtrl.getAll().size()),
                card("Expenses", expenseCtrl.getAll().size())
        );
    }

    private long countStatus(List<Appointment> appts, AppointmentStatus s) {
        return appts.stream().filter(a -> a.getEffectiveStatus() == s).count();
    }

    private VBox card(String title, int value) {
        Label t = new Label(title);
        Label v = new Label(String.valueOf(value));
        v.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1B365D;");
        VBox box = new VBox(4, t, v);
        box.setPrefWidth(170);
        box.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #cccccc; "
                + "-fx-border-radius: 8; -fx-padding: 12;");
        return box;
    }

    // ── Financial ─────────────────────────────────────────────────────────────
    private void buildFinanceRows() {
        allRows.clear();
        for (Invoice inv : invoiceCtrl.getAll()) {
            LocalDate d = inv.getDateHour().toLocalDate();
            if (inv.getProcedures() != null) {
                for (Procedure p : inv.getProcedures()) {
                    double price = p.getPrice() != null ? p.getPrice() : 0.0;
                    allRows.add(new FinanceRow(d, "IN", procedureType(p), price));
                }
            }
            if (inv.getProducts() != null && !inv.getProducts().isEmpty()) {
                double sum = 0.0;
                for (Product pr : inv.getProducts()) sum += pr.getPrice() != null ? pr.getPrice() : 0.0;
                if (sum > 0) allRows.add(new FinanceRow(d, "IN", "Product Sales", sum));
            }
        }
        for (Expense e : expenseCtrl.getAll()) {
            allRows.add(new FinanceRow(e.getDate(), "OUT", e.getType().name(), e.getAmount()));
        }
    }

    private void setupTransactionTable() {
        colTxDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().date.format(DATE)));
        colTxDir.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().direction));
        colTxCategory.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().category));
        colTxAmount.setCellValueFactory(c -> new SimpleStringProperty(money(c.getValue().amount)));
    }

    private void setupInvoiceTable() {
        colInvId.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        colInvOwner.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOwner().getName()));
        colInvAnimal.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPatient().getName()));
        colInvDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateHour().format(DATE_TIME)));
        colInvTotal.setCellValueFactory(c -> new SimpleStringProperty(money(invoiceTotal(c.getValue()))));
        tableInvoices.setItems(FXCollections.observableArrayList(invoiceCtrl.getAll()));
        tableInvoices.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, inv) -> btnExportInvoice.setDisable(inv == null));
        btnExportInvoice.setDisable(true);
    }

    private double invoiceTotal(Invoice inv) {
        double total = 0.0;
        if (inv.getProcedures() != null) {
            for (Procedure p : inv.getProcedures()) total += p.getPrice() != null ? p.getPrice() : 0.0;
        }
        if (inv.getProducts() != null) {
            for (Product pr : inv.getProducts()) total += pr.getPrice() != null ? pr.getPrice() : 0.0;
        }
        return total;
    }

    private void setupCategoryFilter() {
        List<String> categories = allRows.stream().map(r -> r.category).distinct().sorted().collect(Collectors.toList());
        List<String> options = new ArrayList<>();
        options.add("All");
        options.addAll(categories);
        cbCategory.setItems(FXCollections.observableArrayList(options));
        cbCategory.getSelectionModel().select("All");
    }

    @FXML
    private void handleApplyFilter() {
        applyFilter();
    }

    @FXML
    private void handleClearFilter() {
        dpFrom.setValue(null);
        dpTo.setValue(null);
        cbCategory.getSelectionModel().select("All");
        applyFilter();
    }

    private List<FinanceRow> currentFilteredRows() {
        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();
        String category = cbCategory.getValue();
        return allRows.stream()
                .filter(r -> from == null || !r.date.isBefore(from))
                .filter(r -> to == null || !r.date.isAfter(to))
                .filter(r -> category == null || category.equals("All") || category.equals(r.category))
                .collect(Collectors.toList());
    }

    private void applyFilter() {
        List<FinanceRow> filtered = currentFilteredRows();
        tableTransactions.setItems(FXCollections.observableArrayList(filtered));

        double totalIn = filtered.stream().filter(r -> r.direction.equals("IN")).mapToDouble(r -> r.amount).sum();
        double totalOut = filtered.stream().filter(r -> r.direction.equals("OUT")).mapToDouble(r -> r.amount).sum();
        lblTotalIn.setText(money(totalIn));
        lblTotalOut.setText(money(totalOut));
        lblBalance.setText(money(totalIn - totalOut));
    }

    @FXML
    private void handleExportInvoice() {
        Invoice inv = tableInvoices.getSelectionModel().getSelectedItem();
        if (inv == null) return;

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Invoice (PDF)");
        chooser.setInitialFileName("Invoice_" + inv.getId() + ".pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(btnExportInvoice.getScene().getWindow());
        if (dest == null) return;

        try {
            new PdfReportService().generateInvoice(inv, dest);
            showAlert(Alert.AlertType.INFORMATION, "Invoice Exported", "PDF saved to:\n" + dest.getAbsolutePath());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", ex.getMessage());
        }
    }

    @FXML
    private void handleExportFinancial() {
        List<FinanceRow> rows = currentFilteredRows();
        double totalIn = rows.stream().filter(r -> r.direction.equals("IN")).mapToDouble(r -> r.amount).sum();
        double totalOut = rows.stream().filter(r -> r.direction.equals("OUT")).mapToDouble(r -> r.amount).sum();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Financial Report (PDF)");
        chooser.setInitialFileName("FinancialReport.pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(tableTransactions.getScene().getWindow());
        if (dest == null) return;

        try {
            List<String[]> reportRows = rows.stream()
                    .map(r -> new String[]{ r.date.format(DATE), r.direction, r.category, money(r.amount) })
                    .collect(Collectors.toList());
            new PdfReportService().generateFinancialReport(dpFrom.getValue(), dpTo.getValue(),
                    cbCategory.getValue(), totalIn, totalOut, reportRows, dest);
            showAlert(Alert.AlertType.INFORMATION, "Report Exported", "PDF saved to:\n" + dest.getAbsolutePath());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", ex.getMessage());
        }
    }

    private String procedureType(Procedure p) {
        if (p instanceof Appointment) return "Consultation";
        if (p instanceof Surgery) return "Surgery";
        if (p instanceof PetShopService) return "PetShop Service";
        if (p instanceof Hospitalization) return "Hospitalization";
        return "Procedure";
    }

    private String money(double v) {
        return String.format("R$ %.2f", v);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class FinanceRow {
        final LocalDate date;
        final String direction;
        final String category;
        final double amount;

        FinanceRow(LocalDate date, String direction, String category, double amount) {
            this.date = date;
            this.direction = direction;
            this.category = category;
            this.amount = amount;
        }
    }

    @FXML private void handleBack() { gui.Navigator.navigate("Admin", "/view/fxml/AdminMenu.fxml"); }
}
