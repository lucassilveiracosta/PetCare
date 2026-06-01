package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAnimal;
import business.interfaces.IControllerAppointment;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.ExoticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.Anamnesis;
import business.model.appointment.Appointment;
import enums.AppointmentStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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
        String prescription = "Patient: " + selectedAnimal.getName()
                + "\nSpecies: " + selectedAnimal.getSpecies()
                + "\nWeight: " + fieldWeight.getText() + " kg"
                + "\n\nSymptoms / Complaint:\n" + fieldSymptoms.getText()
                + "\n\nDate: " + LocalDate.now().format(DATE_FMT);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Veterinary Prescription");
        alert.setHeaderText("Prescription — " + selectedAnimal.getName());
        alert.setContentText(prescription);
        alert.showAndWait();
    }

    @FXML
    private void finishConsultation() {
        if (selectedAppointment != null && selectedAnimal != null) {
            String complaint = fieldSymptoms.getText();
            if (complaint == null || complaint.isBlank()) {
                complaint = "No complaint recorded.";
            }
            // Setting anamnesis marks this appointment as COMPLETED in getEffectiveStatus()
            selectedAppointment.setAnamnesis(
                    new Anamnesis(complaint, "-", "Consultation finalized"));

            showAlert(Alert.AlertType.INFORMATION, "Consultation Finalized",
                    selectedAnimal.getName() + "'s appointment is now marked as Completed.\n"
                            + "The Dashboard will reflect this change.");
        }

        selectedAnimal = null;
        selectedAppointment = null;
        fieldAnimal.setText("");
        fieldType.setText("");
        fieldWeight.clear();
        fieldSymptoms.clear();
        chkRabiesVaccine.setSelected(false);
        lvClinicalHistory.getItems().clear();
        medicalrecordpanel.setDisable(true);
        listWait.getSelectionModel().clearSelection();
        loadWaitingList();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
