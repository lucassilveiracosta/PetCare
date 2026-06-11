package gui.controllers;
import gui.Navigator;

import business.controller.ControllerPetCareServer;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.Surgery;
import business.model.person.Veterinarian;
import enums.SurgeryRisk;
import exceptions.UnpaidInvoiceException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * REQ08 — Surgery / Hospitalization center. Lists consultations flagged as needing
 * surgery or hospitalization; registering a surgery (surgeon, risk, anesthesia,
 * supplies, procedures) creates a Surgery + Invoice and clears the flag.
 */
public class SurgeryCenterController implements Initializable {

    @FXML private ListView<String> listFlagged;
    @FXML private Label lblPatient;
    @FXML private ChoiceBox<Veterinarian> cbSurgeon;
    @FXML private ChoiceBox<SurgeryRisk> cbRisk;
    @FXML private TextField fieldAnesthesia, fieldPrice;
    @FXML private TextArea fieldSupplies, fieldProcedures;
    @FXML private Button btnRegister;

    private ControllerPetCareServer server;
    private List<Appointment> flagged = new ArrayList<>();
    private Appointment selected;

    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        server = ControllerPetCareServer.getInstance();

        cbSurgeon.setItems(FXCollections.observableArrayList(server.getPerson().getAllVets()));
        cbSurgeon.setConverter(new StringConverter<>() {
            @Override public String toString(Veterinarian v) { return v == null ? "" : v.getName(); }
            @Override public Veterinarian fromString(String s) { return null; }
        });
        cbRisk.setItems(FXCollections.observableArrayList(SurgeryRisk.values()));
        cbRisk.getSelectionModel().select(SurgeryRisk.MEDIUM);

        listFlagged.getSelectionModel().selectedIndexProperty().addListener((obs, old, nw) -> {
            int idx = nw.intValue();
            selected = (idx >= 0 && idx < flagged.size()) ? flagged.get(idx) : null;
            updateForm();
        });

        loadFlagged();
        updateForm();
    }

    private void loadFlagged() {
        flagged = server.getAppointment().getAll().stream()
                .filter(a -> a.isNeedsSurgery() || a.isNeedsHospitalization())
                .collect(Collectors.toList());
        var items = FXCollections.<String>observableArrayList();
        for (Appointment a : flagged) {
            String tags = (a.isNeedsSurgery() ? "[SURGERY]" : "") + (a.isNeedsHospitalization() ? "[HOSPITALIZATION]" : "");
            items.add(a.getPatient().getName() + "  —  " + a.getDateHourScheduled().format(DT) + "  " + tags);
        }
        if (items.isEmpty()) items.add("No consultations need surgery/hospitalization.");
        listFlagged.setItems(items);
    }

    private void updateForm() {
        boolean has = selected != null;
        btnRegister.setDisable(!has);
        if (has) {
            lblPatient.setText("Patient: " + selected.getPatient().getName()
                    + "  ·  from consultation on " + selected.getDateHourScheduled().format(DT));
            if (selected.getResponsibleVeterinarian() != null) cbSurgeon.setValue(selected.getResponsibleVeterinarian());
        } else {
            lblPatient.setText("Select a consultation on the left.");
        }
    }

    @FXML
    private void handleRegister() {
        if (selected == null) return;
        if (cbSurgeon.getValue() == null || cbRisk.getValue() == null
                || fieldAnesthesia.getText() == null || fieldAnesthesia.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Missing data", "Select surgeon, risk and anesthesia type.");
            return;
        }
        try {
            double price = parsePrice(fieldPrice.getText());
            String procedures = orDefault(fieldProcedures.getText(), "Surgical procedure");
            Surgery surgery = new Surgery(true, price, selected.getPatient(), LocalDateTime.now(),
                    procedures, cbRisk.getValue(), cbSurgeon.getValue(), fieldAnesthesia.getText().trim());
            surgery.setSupplies(orDefault(fieldSupplies.getText(), "-"));
            server.getSurgery().post(surgery);

            // Invoice for the surgery (revenue)
            if (selected.getPatient() instanceof DomesticAnimal da) {
                ArrayList<Procedure> procs = new ArrayList<>();
                procs.add(surgery);
                server.getInvoice().post(new Invoice(da.getOwner(), da, procs, new ArrayList<>()));
            }

            // clear the referral flags and persist
            selected.setNeedsSurgery(false);
            selected.setNeedsHospitalization(false);
            server.saveAll();

            showAlert(Alert.AlertType.INFORMATION, "Surgery Registered",
                    "Surgery for " + selected.getPatient().getName() + " registered with "
                            + cbSurgeon.getValue().getName() + ".");
            selected = null;
            clearForm();
            loadFlagged();
            updateForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not register surgery", e.getMessage());
        }
    }

    /** REQ16 - settles every invoice of the selected patient (clears hospital costs). */
    @FXML
    private void handlePayInvoices() {
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhuma seleção", "Selecione uma consulta na lista.");
            return;
        }
        int animalId = selected.getPatient().getId();
        int settled = 0;
        for (Invoice inv : server.getInvoice().getAll()) {
            if (inv.getPatient().getId() == animalId && !inv.isPaid()) {
                server.getInvoice().markAsPaid(inv.getId());
                settled++;
            }
        }
        showAlert(Alert.AlertType.INFORMATION, "Pagamento registrado",
                settled + " fatura(s) de " + selected.getPatient().getName() + " quitada(s).");
    }

    /** REQ16 - discharges the hospitalized animal, blocked while invoices are unpaid. */
    @FXML
    private void handleDischarge() {
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhuma seleção", "Selecione uma consulta na lista.");
            return;
        }
        if (!selected.isNeedsHospitalization()) {
            showAlert(Alert.AlertType.WARNING, "Sem internação",
                    selected.getPatient().getName() + " não está marcado para internação.");
            return;
        }
        try {
            server.getInvoice().validateDischarge(selected.getPatient().getId()); // throws if unpaid
            selected.setNeedsHospitalization(false);
            server.saveAll();
            showAlert(Alert.AlertType.INFORMATION, "Alta concedida",
                    "Alta registrada para " + selected.getPatient().getName() + ".");
            selected = null;
            loadFlagged();
            updateForm();
        } catch (UnpaidInvoiceException e) {
            showAlert(Alert.AlertType.WARNING, "Alta bloqueada", e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        loadFlagged();
    }

    @FXML
    private void handleBack() {
        Navigator.navigate("Consultations", "/view/fxml/Consultation.fxml");
    }

    private void clearForm() {
        fieldAnesthesia.clear();
        fieldSupplies.clear();
        fieldProcedures.clear();
        fieldPrice.setText("300");
        cbRisk.getSelectionModel().select(SurgeryRisk.MEDIUM);
        cbSurgeon.getSelectionModel().clearSelection();
    }

    private static String orDefault(String value, String fallback) {
        return (value != null && !value.isBlank()) ? value.trim() : fallback;
    }

    private static double parsePrice(String s) {
        if (s == null || s.isBlank()) return 0.0;
        return Double.parseDouble(s.trim().replace(',', '.'));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
