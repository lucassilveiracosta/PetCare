package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAppointment;
import business.interfaces.IControllerPerson;
import business.model.appointment.Appointment;
import business.model.person.Veterinarian;
import enums.AppointmentStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminAppointmentsController implements Initializable {

    @FXML private ToggleButton btnAll, btnPending, btnInProgress, btnExpired, btnCompleted;
    @FXML private TableView<Appointment> tableAppointments;
    @FXML private TableColumn<Appointment, String> colStatus, colAnimal, colVet, colDate, colDesc;

    @FXML private DatePicker dateField;
    @FXML private ChoiceBox<String> cbTime;
    @FXML private ChoiceBox<Veterinarian> cbVet;
    @FXML private TextField fieldDescription, fieldPrice;
    @FXML private Button btnSave, btnDelete;

    private IControllerAppointment appointmentCtrl;
    private IControllerPerson personCtrl;
    private ToggleGroup filterGroup;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentCtrl = ControllerPetCareServer.getInstance().getAppointment();
        personCtrl = ControllerPetCareServer.getInstance().getPessoa();

        setupFilters();
        setupTable();
        setupEditPanel();

        tableAppointments.getSelectionModel().selectedItemProperty().addListener((obs, old, appt) -> populateForm(appt));
        refresh();
    }

    private void setupFilters() {
        filterGroup = new ToggleGroup();
        for (ToggleButton b : List.of(btnAll, btnPending, btnInProgress, btnExpired, btnCompleted)) {
            b.setToggleGroup(filterGroup);
        }
        btnAll.setSelected(true);
        filterGroup.selectedToggleProperty().addListener((obs, old, nw) -> {
            if (nw == null) filterGroup.selectToggle(old);
            else applyFilter();
        });
    }

    private void setupTable() {
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(statusLabel(c.getValue().getEffectiveStatus())));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) { setText(null); setStyle(""); return; }
                setText(status);
                String base = "-fx-font-weight: bold; -fx-alignment: CENTER; -fx-padding: 4 8; -fx-background-radius: 4;";
                switch (status) {
                    case "Pending"     -> setStyle(base + "-fx-background-color: #fff3cd; -fx-text-fill: #856404;");
                    case "In Progress" -> setStyle(base + "-fx-background-color: #cce5ff; -fx-text-fill: #004085;");
                    case "Expired"     -> setStyle(base + "-fx-background-color: #f8d7da; -fx-text-fill: #721c24;");
                    case "Completed"   -> setStyle(base + "-fx-background-color: #d4edda; -fx-text-fill: #155724;");
                    default            -> setStyle(base);
                }
            }
        });
        colAnimal.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPatient().getName()));
        colVet.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getResponsableVeterinarian().getName()));
        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateHourScheduled().format(FMT)));
        colDesc.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));
    }

    private void setupEditPanel() {
        // 30-min slots 07:00–19:00
        for (LocalTime t = LocalTime.of(7, 0); !t.isAfter(LocalTime.of(19, 0)); t = t.plusMinutes(30)) {
            cbTime.getItems().add(t.format(TIME_FMT));
        }
        cbVet.setItems(FXCollections.observableArrayList(personCtrl.getAllVets()));
        cbVet.setConverter(new StringConverter<>() {
            @Override public String toString(Veterinarian v) { return v == null ? "" : v.getName(); }
            @Override public Veterinarian fromString(String s) { return null; }
        });
    }

    private void populateForm(Appointment appt) {
        boolean none = appt == null;
        btnSave.setDisable(none);
        btnDelete.setDisable(none);
        if (none) return;
        LocalDateTime dt = appt.getDateHourScheduled();
        dateField.setValue(dt.toLocalDate());
        cbTime.setValue(dt.toLocalTime().format(TIME_FMT));
        cbVet.setValue(appt.getResponsableVeterinarian());
        fieldDescription.setText(appt.getDescription());
        fieldPrice.setText(appt.getPrice() != null ? String.valueOf(appt.getPrice()) : "");
    }

    @FXML
    private void handleSave() {
        Appointment appt = tableAppointments.getSelectionModel().getSelectedItem();
        if (appt == null) return;
        try {
            LocalDate date = dateField.getValue();
            String time = cbTime.getValue();
            if (date == null || time == null) {
                showAlert(Alert.AlertType.WARNING, "Missing data", "Select a date and time.");
                return;
            }
            LocalDateTime newDt = LocalDateTime.of(date, LocalTime.parse(time, TIME_FMT));
            appt.setDateHourScheduled(newDt);           // validates future date
            if (cbVet.getValue() != null) appt.setResponsableVeterinarian(cbVet.getValue());
            if (fieldDescription.getText() != null && !fieldDescription.getText().isBlank()) {
                appt.setDescription(fieldDescription.getText());
            }
            if (fieldPrice.getText() != null && !fieldPrice.getText().isBlank()) {
                appt.setPrice(Double.valueOf(fieldPrice.getText().trim().replace(',', '.')));
            }
            refresh();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Could not save", ex.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Appointment appt = tableAppointments.getSelectionModel().getSelectedItem();
        if (appt == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete this appointment for " + appt.getPatient().getName() + "?",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText(null);
        confirm.showAndWait().filter(b -> b == ButtonType.OK).ifPresent(b -> {
            try {
                appointmentCtrl.delete(appt.getId());
                refresh();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Could not delete", ex.getMessage());
            }
        });
    }

    @FXML
    private void handleRefresh() {
        refresh();
    }

    private void refresh() {
        applyFilter();
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void applyFilter() {
        List<Appointment> all = appointmentCtrl.getAll();
        Toggle sel = filterGroup.getSelectedToggle();
        List<Appointment> filtered;
        if      (sel == btnPending)    filtered = byStatus(all, AppointmentStatus.PENDING);
        else if (sel == btnInProgress) filtered = byStatus(all, AppointmentStatus.IN_PROGRESS);
        else if (sel == btnExpired)    filtered = byStatus(all, AppointmentStatus.EXPIRED);
        else if (sel == btnCompleted)  filtered = byStatus(all, AppointmentStatus.COMPLETED);
        else                           filtered = all;
        tableAppointments.setItems(FXCollections.observableArrayList(filtered));
    }

    private List<Appointment> byStatus(List<Appointment> all, AppointmentStatus s) {
        return all.stream().filter(a -> a.getEffectiveStatus() == s).collect(Collectors.toList());
    }

    private String statusLabel(AppointmentStatus s) {
        return switch (s) {
            case PENDING     -> "Pending";
            case IN_PROGRESS -> "In Progress";
            case EXPIRED     -> "Expired";
            case COMPLETED   -> "Completed";
        };
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
