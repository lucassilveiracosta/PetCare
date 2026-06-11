package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.appointment.Appointment;
import business.model.person.Veterinarian;
import business.report.PdfReportService;
import enums.AppointmentStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * REQ12 — Veterinarian productivity report. Shows each vet's consultations (PDV)
 * and productivity (count, completed, revenue), exportable to PDF.
 */
public class VetProductivityController implements Initializable {

    @FXML private TableView<VetStat> tableVets;
    @FXML private TableColumn<VetStat, String> colVet, colCrmv, colTotal, colCompleted, colRevenue;
    @FXML private Label lblTotalConsults, lblTotalRevenue;

    private List<Veterinarian> vets;
    private List<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControllerPetCareServer server = ControllerPetCareServer.getInstance();
        vets = server.getPerson().getAllVets();
        appointments = server.getAppointment().getAll();

        colVet.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().vet.getName()));
        colCrmv.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().vet.getCrmv()));
        colTotal.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().total)));
        colCompleted.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().completed)));
        colRevenue.setCellValueFactory(c -> new SimpleStringProperty(money(c.getValue().revenue)));

        refresh();
    }

    private void refresh() {
        var rows = FXCollections.<VetStat>observableArrayList();
        int totalConsults = 0;
        double totalRevenue = 0.0;
        for (Veterinarian v : vets) {
            VetStat s = new VetStat(v);
            for (Appointment a : appointments) {
                if (a.getResponsibleVeterinarian() == null || a.getResponsibleVeterinarian().getId() != v.getId()) continue;
                s.total++;
                if (a.getEffectiveStatus() == AppointmentStatus.COMPLETED) s.completed++;
                s.revenue += a.getPrice() != null ? a.getPrice() : 0.0;
            }
            totalConsults += s.total;
            totalRevenue += s.revenue;
            rows.add(s);
        }
        tableVets.setItems(rows);
        lblTotalConsults.setText(String.valueOf(totalConsults));
        lblTotalRevenue.setText(money(totalRevenue));
    }

    @FXML
    private void handleExportPdf() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Productivity Report (PDF)");
        chooser.setInitialFileName("VetProductivityReport.pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(tableVets.getScene().getWindow());
        if (dest == null) return;
        try {
            new PdfReportService().generateVetProductivityReport(vets, appointments, dest);
            showAlert(Alert.AlertType.INFORMATION, "Report generated", "PDF saved to:\n" + dest.getAbsolutePath());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Could not generate report", e.getMessage());
        }
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

    static class VetStat {
        final Veterinarian vet;
        int total = 0;
        int completed = 0;
        double revenue = 0.0;

        VetStat(Veterinarian vet) { this.vet = vet; }
    }
}
