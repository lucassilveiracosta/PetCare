package gui.controllers;

import business.model.appointment.Appointment;
import business.model.appointment.PhysicalExamination;
import business.model.appointment.VitalParameters;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class ConsultationDetailsController {

    @FXML private Label lblTitle;
    @FXML private Label lblAnimal, lblAnimalType, lblVet, lblDate;
    @FXML private Label lblDiagnosis, lblComplaint, lblDietary, lblPrescription;
    @FXML private Label lblConsciousness, lblTemperature, lblHeartRate, lblRespiratoryRate;
    @FXML private Label lblMucosa, lblExamNotes, lblCoagulation, lblHydration, lblVitalNotes;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    public void setAppointment(Appointment appt) {
        lblTitle.setText("Consultation — " + appt.getPatient().getName());

        // Appointment info
        lblAnimal.setText(appt.getPatient().getName());
        lblAnimalType.setText(appt.getPatient().getSpecies() + " · " + appt.getPatient().getRace());
        lblVet.setText(appt.getResponsibleVeterinarian().getName());
        lblDate.setText(appt.getDateHourScheduled().format(FMT));

        // Medical record
        lblDiagnosis.setText(orDash(appt.getDiagnosis()));
        lblPrescription.setText(orDash(appt.getMedicalPrescription()));

        if (appt.getAnamnesis() != null) {
            lblComplaint.setText(orDash(appt.getAnamnesis().getMainComplaint()));
            lblDietary.setText(orDash(appt.getAnamnesis().getDietaryRestriction()));
        } else {
            lblComplaint.setText("-");
            lblDietary.setText("-");
        }

        // Physical exam + vital parameters
        PhysicalExamination exam = appt.getPhisicalExam();
        if (exam != null) {
            lblConsciousness.setText(exam.getLevelOfConsciousness() != null
                    ? exam.getLevelOfConsciousness().name() : "-");
            lblExamNotes.setText(orDash(exam.getDescription()));

            VitalParameters vp = exam.getVitalParameters();
            if (vp != null) {
                lblTemperature.setText(unit(vp.getCelciusTemperature(), "°C"));
                lblHeartRate.setText(unit(vp.getHeartRate(), "bpm"));
                lblRespiratoryRate.setText(unit(vp.getRespiratoryRate(), "rpm"));
                lblMucosa.setText(vp.getMucosa() != null ? vp.getMucosa().name() : "-");
                lblCoagulation.setText(unit(vp.getCoagulation(), "s"));
                lblHydration.setText(hydration(vp.getHydration()));
                lblVitalNotes.setText(orDash(vp.getDescription()));
            } else {
                setVitalsDash();
            }
        } else {
            lblConsciousness.setText("-");
            lblExamNotes.setText("-");
            setVitalsDash();
        }
    }

    private void setVitalsDash() {
        lblTemperature.setText("-");
        lblHeartRate.setText("-");
        lblRespiratoryRate.setText("-");
        lblMucosa.setText("-");
        lblCoagulation.setText("-");
        lblHydration.setText("-");
        lblVitalNotes.setText("-");
    }

    private String unit(Number value, String unit) {
        return value != null ? value + " " + unit : "-";
    }

    private String hydration(business.model.appointment.Hydration h) {
        if (h == null) return "-";
        if (h.isEuvolemic()) return "Euvolemic";
        return h.getDehydration() != null
                ? "Dehydrated (" + h.getDehydration() + "%)" : "Dehydrated";
    }

    @FXML
    private void handleClose() {
        ((Stage) lblTitle.getScene().getWindow()).close();
    }

    private String orDash(String value) {
        return (value != null && !value.isBlank()) ? value : "-";
    }
}
