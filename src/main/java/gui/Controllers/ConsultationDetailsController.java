package gui.Controllers;

import business.model.appointment.Appointment;
import business.model.appointment.PhysicalExamination;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class ConsultationDetailsController {

    @FXML private Label lblTitle;
    @FXML private Label lblAnimal, lblAnimalType, lblVet, lblDate;
    @FXML private Label lblDiagnosis, lblComplaint, lblDietary, lblPrescription;
    @FXML private Label lblConsciousness, lblTemperature, lblHeartRate, lblRespiratoryRate;
    @FXML private Label lblMucosa, lblExamNotes;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    public void setAppointment(Appointment appt) {
        lblTitle.setText("Consultation — " + appt.getPatient().getName());

        // Appointment info
        lblAnimal.setText(appt.getPatient().getName());
        lblAnimalType.setText(appt.getPatient().getSpecies() + " · " + appt.getPatient().getRace());
        lblVet.setText(appt.getResponsableVeterinarian().getName());
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

        // Physical exam
        PhysicalExamination exam = appt.getPhisicalExam();
        if (exam != null) {
            lblConsciousness.setText(exam.getLevelOfConsciousness().name());
            lblExamNotes.setText(orDash(exam.getDescription()));
            if (exam.getVitalParameters() != null) {
                lblTemperature.setText(exam.getVitalParameters().getCelciusTemperature() + " °C");
                lblHeartRate.setText(exam.getVitalParameters().getHeartRate() + " bpm");
                lblRespiratoryRate.setText(exam.getVitalParameters().getRespiratoryRate() + " rpm");
                lblMucosa.setText(exam.getVitalParameters().getMucosa().name());
            }
        } else {
            lblConsciousness.setText("-");
            lblTemperature.setText("-");
            lblHeartRate.setText("-");
            lblRespiratoryRate.setText("-");
            lblMucosa.setText("-");
            lblExamNotes.setText("-");
        }
    }

    @FXML
    private void handleClose() {
        ((Stage) lblTitle.getScene().getWindow()).close();
    }

    private String orDash(String value) {
        return (value != null && !value.isBlank()) ? value : "-";
    }
}
