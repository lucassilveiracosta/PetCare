package gui;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.appointment.Appointment;
import business.report.PdfReportService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * REQ13 — Export a pet's clinical history for a chosen period (week / month /
 * custom range): lists the consultations in that window and exports to PDF.
 */
public class AdminClinicalHistoryController implements Initializable {

    @FXML private ChoiceBox<String> cbPet;
    @FXML private ChoiceBox<String> cbPeriod;
    @FXML private DatePicker dpFrom, dpTo;
    @FXML private TableView<Appointment> tableConsults;
    @FXML private TableColumn<Appointment, String> colDate, colVet, colDiagnosis, colStatus, colPrice;
    @FXML private Label lblCount;

    private List<Animal> animals;
    private List<Appointment> filtered = new ArrayList<>();

    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter D = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animals = ControllerPetCareServer.getInstance().getAnimal().getAll();
        for (Animal a : animals) cbPet.getItems().add(a.getName());
        if (!animals.isEmpty()) cbPet.getSelectionModel().selectFirst();

        cbPeriod.getItems().addAll("This week", "This month", "Custom range");
        cbPeriod.getSelectionModel().select("This month");
        cbPeriod.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> applyPeriodPreset(nv));
        applyPeriodPreset("This month");

        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateHourScheduled().format(DT)));
        colVet.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getResponsableVeterinarian() != null ? c.getValue().getResponsableVeterinarian().getName() : "-"));
        colDiagnosis.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDiagnosis() != null ? c.getValue().getDiagnosis() : "-"));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEffectiveStatus().name()));
        colPrice.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getPrice() != null ? String.format("R$ %.2f", c.getValue().getPrice()) : "-"));
    }

    private void applyPeriodPreset(String period) {
        LocalDate today = LocalDate.now();
        if ("This week".equals(period)) {
            LocalDate monday = today.with(DayOfWeek.MONDAY);
            dpFrom.setValue(monday);
            dpTo.setValue(monday.plusDays(6));
        } else if ("This month".equals(period)) {
            dpFrom.setValue(today.withDayOfMonth(1));
            dpTo.setValue(today.withDayOfMonth(today.lengthOfMonth()));
        }
        // "Custom range" → keep whatever the admin picked
    }

    private Animal selectedPet() {
        int idx = cbPet.getSelectionModel().getSelectedIndex();
        return (idx >= 0 && idx < animals.size()) ? animals.get(idx) : null;
    }

    @FXML
    private void handleShow() {
        Animal pet = selectedPet();
        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();
        if (pet == null || from == null || to == null) {
            showAlert(Alert.AlertType.WARNING, "Missing data", "Select a pet and a date range.");
            return;
        }
        filtered = ControllerPetCareServer.getInstance().getAppointment().getAll().stream()
                .filter(a -> a.getPatient().getId() == pet.getId())
                .filter(a -> {
                    LocalDate d = a.getDateHourScheduled().toLocalDate();
                    return !d.isBefore(from) && !d.isAfter(to);
                })
                .sorted(Comparator.comparing(Appointment::getDateHourScheduled))
                .collect(Collectors.toList());
        tableConsults.setItems(FXCollections.observableArrayList(filtered));
        lblCount.setText(String.valueOf(filtered.size()));
    }

    @FXML
    private void handleExportPdf() {
        Animal pet = selectedPet();
        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();
        if (pet == null || from == null || to == null) {
            showAlert(Alert.AlertType.WARNING, "Missing data", "Select a pet and a date range.");
            return;
        }
        handleShow(); // make sure the list reflects the current selection

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Clinical History (PDF)");
        chooser.setInitialFileName("ClinicalHistory_" + pet.getName() + ".pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(tableConsults.getScene().getWindow());
        if (dest == null) return;

        String period = cbPeriod.getValue() + " (" + from.format(D) + " to " + to.format(D) + ")";
        try {
            new PdfReportService().generateClinicalHistory(pet, filtered, period, dest);
            showAlert(Alert.AlertType.INFORMATION, "History Exported", "PDF saved to:\n" + dest.getAbsolutePath());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
