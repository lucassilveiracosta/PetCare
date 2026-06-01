package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAppointment;
import business.model.appointment.Appointment;
import enums.AppointmentStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppointmentDashboardController implements Initializable {

    @FXML private Label cntPending, cntInProgress, cntExpired, cntCompleted;
    @FXML private VBox cardPending, cardInProgress, cardExpired, cardCompleted;

    @FXML private ToggleButton btnAll, btnPending, btnInProgress, btnExpired, btnCompleted;

    @FXML private TableView<Appointment> tableAppointments;
    @FXML private TableColumn<Appointment, String> colStatus, colAnimal, colVet, colDate, colDesc;

    @FXML private Button btnViewConsultation;

    private IControllerAppointment appointmentCtrl;
    private ToggleGroup filterGroup;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentCtrl = ControllerPetCareServer.getInstance().getAppointment();
        setupToggleGroup();
        setupTableColumns();
        setupSelectionListener();
        refresh();
    }

    private void setupToggleGroup() {
        filterGroup = new ToggleGroup();
        btnAll.setToggleGroup(filterGroup);
        btnPending.setToggleGroup(filterGroup);
        btnInProgress.setToggleGroup(filterGroup);
        btnExpired.setToggleGroup(filterGroup);
        btnCompleted.setToggleGroup(filterGroup);
        btnAll.setSelected(true);

        filterGroup.selectedToggleProperty().addListener((obs, old, nw) -> {
            if (nw == null) filterGroup.selectToggle(old);
        });

        cardPending.setOnMouseClicked(e    -> { filterGroup.selectToggle(btnPending);    applyFilter(); });
        cardInProgress.setOnMouseClicked(e -> { filterGroup.selectToggle(btnInProgress); applyFilter(); });
        cardExpired.setOnMouseClicked(e    -> { filterGroup.selectToggle(btnExpired);    applyFilter(); });
        cardCompleted.setOnMouseClicked(e  -> { filterGroup.selectToggle(btnCompleted);  applyFilter(); });
    }

    private void setupTableColumns() {
        colStatus.setCellValueFactory(cell ->
                new SimpleStringProperty(statusLabel(cell.getValue().getEffectiveStatus())));
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

        colAnimal.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getPatient().getName()));
        colVet.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getResponsableVeterinarian().getName()));
        colDate.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDateHourScheduled().format(FMT)));
        colDesc.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDescription()));
    }

    private void setupSelectionListener() {
        tableAppointments.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            boolean isCompleted = selected != null && selected.getEffectiveStatus() == AppointmentStatus.COMPLETED;
            btnViewConsultation.setDisable(!isCompleted);
        });

        tableAppointments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) openConsultationDetails();
        });
    }

    @FXML
    private void handleViewConsultation() {
        openConsultationDetails();
    }

    private void openConsultationDetails() {
        Appointment selected = tableAppointments.getSelectionModel().getSelectedItem();
        if (selected == null || selected.getEffectiveStatus() != AppointmentStatus.COMPLETED) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/fxml/ConsultationDetails.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableAppointments.getScene().getWindow());
            stage.setScene(new Scene(loader.load(), 680, 560));
            stage.setTitle("Consultation — " + selected.getPatient().getName());

            ConsultationDetailsController ctrl = loader.getController();
            ctrl.setAppointment(selected);

            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not open Consultation Details");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private String statusLabel(AppointmentStatus s) {
        return switch (s) {
            case PENDING     -> "Pending";
            case IN_PROGRESS -> "In Progress";
            case EXPIRED     -> "Expired";
            case COMPLETED   -> "Completed";
        };
    }

    @FXML
    private void handleFilter() {
        applyFilter();
    }

    @FXML
    private void handleRefresh() {
        refresh();
    }

    private void refresh() {
        List<Appointment> all = appointmentCtrl.getAll();
        cntPending.setText(String.valueOf(all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.PENDING).count()));
        cntInProgress.setText(String.valueOf(all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.IN_PROGRESS).count()));
        cntExpired.setText(String.valueOf(all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.EXPIRED).count()));
        cntCompleted.setText(String.valueOf(all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.COMPLETED).count()));
        applyFilter();
    }

    private void applyFilter() {
        List<Appointment> all = appointmentCtrl.getAll();
        Toggle selected = filterGroup.getSelectedToggle();

        List<Appointment> filtered;
        if      (selected == btnPending)    filtered = all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.PENDING).collect(Collectors.toList());
        else if (selected == btnInProgress) filtered = all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.IN_PROGRESS).collect(Collectors.toList());
        else if (selected == btnExpired)    filtered = all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.EXPIRED).collect(Collectors.toList());
        else if (selected == btnCompleted)  filtered = all.stream().filter(a -> a.getEffectiveStatus() == AppointmentStatus.COMPLETED).collect(Collectors.toList());
        else                                filtered = all;

        tableAppointments.setItems(FXCollections.observableArrayList(filtered));
        btnViewConsultation.setDisable(true);
    }
}
