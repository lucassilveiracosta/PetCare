package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.appointment.Hydration;
import business.model.appointment.VitalParameters;
import business.model.invoice.Hospitalization;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.person.Veterinarian;
import enums.Mucosa;
import exceptions.UnpaidInvoiceException;
import gui.Navigator;
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
 * REQ07 — Hospitalization (internação) with monitoring. Admit a patient (flagged
 * for hospitalization), record vital-parameter readings over time, and discharge
 * (REQ16: blocked while the patient has an unpaid invoice).
 */
public class HospitalizationController implements Initializable {

    @FXML private ListView<String> listToAdmit, listActive;
    @FXML private Label lblAdmitPatient, lblSelectedHosp, lblVitalsCount;
    @FXML private ChoiceBox<Veterinarian> cbVet;
    @FXML private ChoiceBox<Mucosa> cbMucosa;
    @FXML private TextField fieldPrice, fieldHeartRate, fieldRespRate, fieldTemperature, fieldCoagulation, fieldDehydration;
    @FXML private TextArea fieldDescription, fieldVitalNotes;
    @FXML private CheckBox chkEuvolemic;
    @FXML private Button btnAdmit, btnAddVital, btnPay, btnDischarge;

    private ControllerPetCareServer server;
    private List<Appointment> toAdmit = new ArrayList<>();
    private List<Hospitalization> active = new ArrayList<>();
    private Appointment selectedAppt;
    private Hospitalization selectedHosp;

    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        server = ControllerPetCareServer.getInstance();

        cbVet.setItems(FXCollections.observableArrayList(server.getPerson().getAllVets()));
        cbVet.setConverter(new StringConverter<>() {
            @Override public String toString(Veterinarian v) { return v == null ? "" : v.getName(); }
            @Override public Veterinarian fromString(String s) { return null; }
        });
        cbMucosa.setItems(FXCollections.observableArrayList(Mucosa.values()));

        listToAdmit.getSelectionModel().selectedIndexProperty().addListener((o, old, nw) -> {
            int i = nw.intValue();
            selectedAppt = (i >= 0 && i < toAdmit.size()) ? toAdmit.get(i) : null;
            lblAdmitPatient.setText(selectedAppt != null
                    ? "Paciente: " + selectedAppt.getPatient().getName()
                    : "Nenhum paciente selecionado.");
        });
        listActive.getSelectionModel().selectedIndexProperty().addListener((o, old, nw) -> {
            int i = nw.intValue();
            selectedHosp = (i >= 0 && i < active.size()) ? active.get(i) : null;
            updateHospLabels();
        });

        loadLists();
    }

    private void loadLists() {
        toAdmit = server.getAppointment().getAll().stream()
                .filter(Appointment::isNeedsHospitalization)
                .collect(Collectors.toList());
        active = server.getHospitalization().getAll().stream()
                .filter(Hospitalization::isActive)
                .collect(Collectors.toList());

        var admitItems = FXCollections.<String>observableArrayList();
        for (Appointment a : toAdmit) admitItems.add(a.getPatient().getName() + "  —  " + a.getDateHourScheduled().format(DT));
        if (admitItems.isEmpty()) admitItems.add("Ninguém marcado para internação.");
        listToAdmit.setItems(admitItems);

        var activeItems = FXCollections.<String>observableArrayList();
        for (Hospitalization h : active) activeItems.add(h.getPatient().getName() + "  —  entrada "
                + h.getEntryDateTime().format(DT) + "  (" + h.getVitalParametersHistory().size() + " leituras)");
        if (activeItems.isEmpty()) activeItems.add("Nenhum animal internado.");
        listActive.setItems(activeItems);
    }

    private void updateHospLabels() {
        if (selectedHosp != null) {
            lblSelectedHosp.setText("Internação: " + selectedHosp.getPatient().getName()
                    + "  ·  entrada " + selectedHosp.getEntryDateTime().format(DT));
            lblVitalsCount.setText("Leituras registradas: " + selectedHosp.getVitalParametersHistory().size());
        } else {
            lblSelectedHosp.setText("Nenhuma internação selecionada.");
            lblVitalsCount.setText("Leituras registradas: 0");
        }
    }

    @FXML
    private void handleAdmit() {
        if (selectedAppt == null) {
            showAlert(Alert.AlertType.WARNING, "Sem seleção", "Selecione um paciente na lista 'A internar'.");
            return;
        }
        if (cbVet.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Dados incompletos", "Selecione o veterinário responsável.");
            return;
        }
        try {
            Animal patient = selectedAppt.getPatient();
            double price = parsePrice(fieldPrice.getText());
            String desc = orDefault(fieldDescription.getText(), "Internação");
            LocalDateTime now = LocalDateTime.now();

            Hospitalization h = new Hospitalization(true, price, patient, now, desc,
                    cbVet.getValue(), now, null, new ArrayList<>());
            server.getHospitalization().post(h);

            // Invoice (unpaid) for the hospitalization → revenue + enables REQ16
            if (patient instanceof DomesticAnimal da) {
                ArrayList<Procedure> procs = new ArrayList<>();
                procs.add(h);
                server.getInvoice().post(new Invoice(da.getOwner(), da, procs, new ArrayList<>()));
            }

            selectedAppt.setNeedsHospitalization(false);
            server.saveAll();
            showAlert(Alert.AlertType.INFORMATION, "Internado",
                    patient.getName() + " foi internado. Fatura gerada (pendente).");
            selectedAppt = null;
            loadLists();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Não foi possível internar", e.getMessage());
        }
    }

    @FXML
    private void handleAddVital() {
        if (selectedHosp == null) {
            showAlert(Alert.AlertType.WARNING, "Sem seleção", "Selecione uma internação ativa.");
            return;
        }
        try {
            VitalParameters vp = new VitalParameters(
                    parseInt(fieldHeartRate.getText()),
                    parseInt(fieldRespRate.getText()),
                    parseDouble(fieldTemperature.getText()),
                    cbMucosa.getValue(),
                    parseInt(fieldCoagulation.getText()),
                    new Hydration(chkEuvolemic.isSelected(), parseDouble(fieldDehydration.getText())),
                    orDefault(fieldVitalNotes.getText(), "Monitoramento " + LocalDateTime.now().format(DT)));
            selectedHosp.getVitalParametersHistory().add(vp);
            server.saveAll();
            updateHospLabels();
            loadLists();
            clearVitalFields();
            showAlert(Alert.AlertType.INFORMATION, "Leitura registrada",
                    "Parâmetros vitais adicionados (" + selectedHosp.getVitalParametersHistory().size() + " no total).");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Não foi possível registrar", e.getMessage());
        }
    }

    /** REQ16 — settle the patient's invoices so the discharge is allowed. */
    @FXML
    private void handlePayInvoices() {
        if (selectedHosp == null) {
            showAlert(Alert.AlertType.WARNING, "Sem seleção", "Selecione uma internação ativa.");
            return;
        }
        int animalId = selectedHosp.getPatient().getId();
        int settled = 0;
        for (Invoice inv : server.getInvoice().getAll()) {
            if (inv.getPatient().getId() == animalId && !inv.isPaid()) {
                server.getInvoice().markAsPaid(inv.getId());
                settled++;
            }
        }
        showAlert(Alert.AlertType.INFORMATION, "Pagamento registrado",
                settled + " fatura(s) de " + selectedHosp.getPatient().getName() + " quitada(s).");
    }

    /** REQ16 — discharge, blocked while the patient has an unpaid invoice. */
    @FXML
    private void handleDischarge() {
        if (selectedHosp == null) {
            showAlert(Alert.AlertType.WARNING, "Sem seleção", "Selecione uma internação ativa.");
            return;
        }
        try {
            server.getInvoice().validateDischarge(selectedHosp.getPatient().getId()); // throws if unpaid
            selectedHosp.setDischargeDateTime(LocalDateTime.now());
            server.saveAll();
            showAlert(Alert.AlertType.INFORMATION, "Alta concedida",
                    "Alta registrada para " + selectedHosp.getPatient().getName() + ".");
            selectedHosp = null;
            updateHospLabels();
            loadLists();
        } catch (UnpaidInvoiceException e) {
            showAlert(Alert.AlertType.WARNING, "Alta bloqueada", e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        loadLists();
    }

    @FXML
    private void handleBack() {
        Navigator.navigate("Admin", "/view/fxml/AdminMenu.fxml");
    }

    private void clearVitalFields() {
        fieldHeartRate.clear();
        fieldRespRate.clear();
        fieldTemperature.clear();
        fieldCoagulation.clear();
        fieldDehydration.clear();
        fieldVitalNotes.clear();
    }

    private static String orDefault(String value, String fallback) {
        return (value != null && !value.isBlank()) ? value.trim() : fallback;
    }

    private static double parsePrice(String s) {
        if (s == null || s.isBlank()) return 0.0;
        return Double.parseDouble(s.trim().replace(',', '.'));
    }

    private static Integer parseInt(String s) {
        if (s == null || s.isBlank()) return null;
        return Integer.valueOf(s.trim());
    }

    private static Double parseDouble(String s) {
        if (s == null || s.isBlank()) return null;
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
