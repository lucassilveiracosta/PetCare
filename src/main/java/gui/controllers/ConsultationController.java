package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAnimal;
import business.interfaces.IControllerAppointment;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.ExoticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.Anamnesis;
import business.model.appointment.Appointment;
import business.model.appointment.Hydration;
import business.model.appointment.PhysicalExamination;
import business.model.appointment.VitalParameters;
import business.report.PdfReportService;
import enums.AppointmentStatus;
import enums.Conscience;
import enums.Mucosa;
import enums.ProcedureType;
import gui.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConsultationController implements Initializable {

    @FXML private ListView<String> listWait;
    @FXML private VBox medicalrecordpanel;
    @FXML private TextField fieldAnimal;
    @FXML private TextField fieldType;
    @FXML private TextField fieldWeight;
    @FXML private TextArea fieldSymptoms;
    @FXML private ListView<String> lvClinicalHistory;
    @FXML private CheckBox chkRabiesVaccine;
    @FXML private Button registerVaccine;
    @FXML private Button issuePrescriptionPDF;

    // Medical record / vet visit
    @FXML private TextArea fieldDiagnosis;
    @FXML private TextArea fieldPrescription;
    @FXML private TextField fieldDietary;

    // Physical examination
    @FXML private ChoiceBox<Conscience> cbConsciousness;
    @FXML private TextArea fieldExamNotes;

    // Vital parameters
    @FXML private TextField fieldHeartRate;
    @FXML private TextField fieldRespiratoryRate;
    @FXML private TextField fieldTemperature;
    @FXML private TextField fieldCoagulation;
    @FXML private ChoiceBox<Mucosa> cbMucosa;
    @FXML private CheckBox chkEuvolemic;
    @FXML private TextField fieldDehydration;
    @FXML private TextArea fieldVitalNotes;

    // Procedure / referral
    @FXML private ChoiceBox<ProcedureType> cbProcedure;
    @FXML private CheckBox chkHospitalization;
    @FXML private Button btnSurgeryCenter;

    private IControllerAnimal animalController;
    private IControllerAppointment appointmentController;

    private List<Animal> waitingAnimals = new ArrayList<>();
    private List<Appointment> waitingAppointments = new ArrayList<>();
    private Animal selectedAnimal;
    private Appointment selectedAppointment;

    private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter DATE_FMT      = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerPetCareServer server = ControllerPetCareServer.getInstance();
        animalController    = server.getAnimal();
        appointmentController = server.getAppointment();

        cbConsciousness.setItems(FXCollections.observableArrayList(Conscience.values()));
        cbConsciousness.getSelectionModel().select(Conscience.ALERTA);
        cbMucosa.setItems(FXCollections.observableArrayList(Mucosa.values()));
        cbMucosa.getSelectionModel().select(Mucosa.NORMACORADAS);
        cbProcedure.setItems(FXCollections.observableArrayList(ProcedureType.values()));
        cbProcedure.getSelectionModel().select(ProcedureType.GENERAL_CONSULTATION);

        medicalrecordpanel.setDisable(true);
        loadWaitingList();

        listWait.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int idx = newVal.intValue();
            if (idx >= 0 && idx < waitingAnimals.size()) {
                selectedAppointment = waitingAppointments.get(idx);
                loadMedicalRecord(waitingAnimals.get(idx));
            }
        });

        registerVaccine.setOnAction(e -> registerRabiesVaccine());
        issuePrescriptionPDF.setOnAction(e -> issuePrescription());
    }

    public void loadWaitingList() {
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm MM/dd");

        waitingAppointments = appointmentController.getAll().stream()
                .filter(a -> a.getEffectiveStatus() == AppointmentStatus.PENDING
                          || a.getEffectiveStatus() == AppointmentStatus.IN_PROGRESS)
                .sorted(Comparator.comparing(Appointment::getDateHourScheduled))
                .collect(Collectors.toList());

        waitingAnimals = waitingAppointments.stream()
                .map(Appointment::getPatient)
                .collect(Collectors.toList());

        ObservableList<String> names = FXCollections.observableArrayList();
        for (Appointment appt : waitingAppointments) {
            Animal a = appt.getPatient();
            names.add(appt.getDateHourScheduled().format(timeFmt)
                    + "  —  " + a.getName() + " (" + a.getSpecies() + ")");
        }
        listWait.setItems(names);
    }

    private void loadMedicalRecord(Animal animal) {
        selectedAnimal = animal;
        medicalrecordpanel.setDisable(false);

        fieldAnimal.setText("Patient: " + animal.getName());

        String type;
        if (animal instanceof DomesticAnimal) type = "Domestic";
        else if (animal instanceof ExoticAnimal) type = "Exotic";
        else type = animal.getSpecies();
        fieldType.setText("Type: " + type);

        fieldWeight.setText(String.valueOf(animal.getWeight()));
        fieldSymptoms.clear();
        clearConsultationFields();

        boolean hasRabiesVaccine = animalController.checkIfHaveRabbiesVaccine(animal.getId());
        chkRabiesVaccine.setSelected(hasRabiesVaccine);

        loadMedicalHistory(animal);
    }

    private void loadMedicalHistory(Animal animal) {
        ObservableList<String> history = FXCollections.observableArrayList();
        for (Appointment appt : appointmentController.getAll()) {
            if (appt.getPatient().getId() == animal.getId()
                    && appt.getEffectiveStatus() == AppointmentStatus.COMPLETED) {
                history.add(appt.getDateHourScheduled().format(DATE_TIME_FMT)
                        + " — " + appt.getDiagnosis());
            }
        }
        if (history.isEmpty()) history.add("No records found.");
        lvClinicalHistory.setItems(history);
    }

    private void registerRabiesVaccine() {
        if (selectedAnimal == null) return;
        if (animalController.checkIfHaveRabbiesVaccine(selectedAnimal.getId())) {
            showAlert(Alert.AlertType.WARNING, "Vaccine Already Registered",
                    selectedAnimal.getName() + " already has a valid rabies vaccine.");
            return;
        }
        LocalDate today = LocalDate.now();
        Vaccine vaccine = new Vaccine("Rabies", today, "Administered during consultation",
                true, today.plusYears(1));
        selectedAnimal.getVaccines().add(vaccine);
        chkRabiesVaccine.setSelected(true);
        showAlert(Alert.AlertType.INFORMATION, "Vaccine Registered",
                "Rabies vaccine registered for " + selectedAnimal.getName()
                        + ".\nExpiry: " + today.plusYears(1).format(DATE_FMT));
    }

    private void issuePrescription() {
        if (selectedAnimal == null) return;

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Clinical History (PDF)");
        chooser.setInitialFileName("ClinicalHistory_" + selectedAnimal.getName() + ".pdf");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File dest = chooser.showSaveDialog(issuePrescriptionPDF.getScene().getWindow());
        if (dest == null) return;

        List<Appointment> history = appointmentController.getAll().stream()
                .filter(a -> a.getPatient().getId() == selectedAnimal.getId()
                        && a.getEffectiveStatus() == AppointmentStatus.COMPLETED)
                .sorted(Comparator.comparing(Appointment::getDateHourScheduled))
                .collect(Collectors.toList());

        try {
            new PdfReportService().generateClinicalHistory(selectedAnimal, history, dest);
            showAlert(Alert.AlertType.INFORMATION, "Clinical History Exported",
                    "PDF saved to:\n" + dest.getAbsolutePath());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", ex.getMessage());
        }
    }

    @FXML
    private void finishConsultation() {
        boolean goToSurgery = false;
        if (selectedAppointment != null && selectedAnimal != null) {
            try {
                String complaint = orDefault(fieldSymptoms.getText(), "No complaint recorded.");
                String dietary   = orDefault(fieldDietary.getText(), "-");

                // Vital parameters (numeric fields are optional → null when blank)
                Hydration hydration = new Hydration(
                        chkEuvolemic.isSelected(),
                        parseDoubleOrNull(fieldDehydration.getText()));

                VitalParameters vitals = new VitalParameters(
                        parseIntOrNull(fieldHeartRate.getText()),
                        parseIntOrNull(fieldRespiratoryRate.getText()),
                        parseDoubleOrNull(fieldTemperature.getText()),
                        cbMucosa.getValue(),
                        parseIntOrNull(fieldCoagulation.getText()),
                        hydration,
                        orDefault(fieldVitalNotes.getText(), "-"));

                PhysicalExamination exam = new PhysicalExamination(
                        cbConsciousness.getValue(),
                        vitals,
                        orDefault(fieldExamNotes.getText(), "-"));

                selectedAppointment.setDiagnosis(orDefault(fieldDiagnosis.getText(), "No diagnosis recorded."));
                selectedAppointment.setMedicalPrescription(orDefault(fieldPrescription.getText(), "-"));
                selectedAppointment.setPhisicalExam(exam);
                // Setting anamnesis marks this appointment as COMPLETED in getEffectiveStatus()
                selectedAppointment.setAnamnesis(
                        new Anamnesis(complaint, dietary, "Consultation finalized"));

                // Procedure referral: surgery and/or hospitalization
                boolean needsSurgery = cbProcedure.getValue() == ProcedureType.SURGERY;
                boolean needsHosp = chkHospitalization.isSelected();
                selectedAppointment.setNeedsSurgery(needsSurgery);
                selectedAppointment.setNeedsHospitalization(needsHosp);
                goToSurgery = needsSurgery || needsHosp;

                // Persist the finalized consultation (diagnosis + exam + vitals) to the CSV database
                business.controller.ControllerPetCareServer.getInstance().saveAll();

                showAlert(Alert.AlertType.INFORMATION, "Consultation Finalized",
                        selectedAnimal.getName() + "'s appointment is now marked as Completed.\n"
                                + "The Dashboard will reflect this change.");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Data",
                        "Could not finalize the consultation:\n" + ex.getMessage());
                return;
            }
        }

        selectedAnimal = null;
        selectedAppointment = null;
        fieldAnimal.setText("");
        fieldType.setText("");
        fieldWeight.clear();
        fieldSymptoms.clear();
        clearConsultationFields();
        chkRabiesVaccine.setSelected(false);
        lvClinicalHistory.getItems().clear();
        medicalrecordpanel.setDisable(true);
        listWait.getSelectionModel().clearSelection();
        loadWaitingList();

        // If surgery/hospitalization was requested, go to the Surgery Center
        if (goToSurgery) {
            Navigator.navigate("Surgery Center", "/view/fxml/SurgeryCenter.fxml");
        }
    }

    @FXML
    private void openSurgeryCenter() {
        Navigator.navigate("Surgery Center", "/view/fxml/SurgeryCenter.fxml");
    }

    private void clearConsultationFields() {
        fieldDiagnosis.clear();
        fieldPrescription.clear();
        fieldDietary.clear();
        fieldExamNotes.clear();
        fieldHeartRate.clear();
        fieldRespiratoryRate.clear();
        fieldTemperature.clear();
        fieldCoagulation.clear();
        fieldDehydration.clear();
        fieldVitalNotes.clear();
        chkEuvolemic.setSelected(true);
        cbConsciousness.getSelectionModel().select(Conscience.ALERTA);
        cbMucosa.getSelectionModel().select(Mucosa.NORMACORADAS);
        cbProcedure.getSelectionModel().select(ProcedureType.GENERAL_CONSULTATION);
        chkHospitalization.setSelected(false);
    }

    private static String orDefault(String value, String fallback) {
        return (value != null && !value.isBlank()) ? value.trim() : fallback;
    }

    private static Integer parseIntOrNull(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double parseDoubleOrNull(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Double.valueOf(value.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
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
