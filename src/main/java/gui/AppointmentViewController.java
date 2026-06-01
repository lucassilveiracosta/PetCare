package gui;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.person.Owner;
import business.model.person.Veterinarian;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentViewController implements Initializable {

    @FXML private ChoiceBox<String> nameTutorScheduling;
    @FXML private ChoiceBox<String> nameAnimalScheduling;
    @FXML private ChoiceBox<String> appointmentScheduling;
    @FXML private ChoiceBox<String> nameVeterianarianScheduling;
    @FXML private DatePicker schedulingDate;
    @FXML private TextField reasonScheduling;
    @FXML private Button scheduleScheduling;

    private List<Owner> allOwners = new ArrayList<>();
    private List<Animal> animalsByOwner = new ArrayList<>();
    private List<Veterinarian> allVets = new ArrayList<>();
    private ControllerPetCareServer server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        server = ControllerPetCareServer.getInstance();

        // Service types
        appointmentScheduling.getItems().addAll(
                "General Consultation", "Vaccination", "Surgery",
                "Emergency", "Follow-up", "Checkup", "Other");

        // Owners
        allOwners = server.getPessoa().getAllOwners();
        for (Owner o : allOwners) {
            nameTutorScheduling.getItems().add(o.getName());
        }

        // When owner changes, refresh animal list
        nameTutorScheduling.getSelectionModel().selectedIndexProperty().addListener(
                (obs, oldVal, newIdx) -> refreshAnimalList(newIdx.intValue()));

        // Vets
        allVets = server.getPessoa().getAllVets();
        for (Veterinarian v : allVets) {
            nameVeterianarianScheduling.getItems().add(v.getName());
        }
    }

    private void refreshAnimalList(int ownerIndex) {
        nameAnimalScheduling.getItems().clear();
        animalsByOwner.clear();
        if (ownerIndex < 0 || ownerIndex >= allOwners.size()) return;

        Owner selectedOwner = allOwners.get(ownerIndex);
        for (Animal a : server.getAnimal().getAll()) {
            if (a instanceof DomesticAnimal da && da.getOwner().getId() == selectedOwner.getId()) {
                nameAnimalScheduling.getItems().add(a.getName() + " (" + a.getSpecies() + ")");
                animalsByOwner.add(a);
            }
        }
    }

    @FXML
    protected void onScheduleClick(ActionEvent event) {
        int animalIdx = nameAnimalScheduling.getSelectionModel().getSelectedIndex();
        int vetIdx    = nameVeterianarianScheduling.getSelectionModel().getSelectedIndex();
        LocalDate date       = schedulingDate.getValue();
        String reason        = reasonScheduling.getText();
        String serviceType   = appointmentScheduling.getValue();

        if (animalIdx < 0 || vetIdx < 0 || date == null
                || serviceType == null
                || reason == null || reason.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Missing fields",
                    "Please fill in all fields before scheduling.");
            return;
        }

        Animal animal = animalsByOwner.get(animalIdx);
        Veterinarian vet = allVets.get(vetIdx);
        String description = serviceType + " — " + reason;
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(9, 0));

        try {
            Appointment appointment = new Appointment(100.0, animal, dateTime, description, vet);
            server.getAppointment().post(appointment);
            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Appointment scheduled successfully!\nIt will appear in the Dashboard.");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not schedule: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameTutorScheduling.getSelectionModel().clearSelection();
        nameAnimalScheduling.getItems().clear();
        nameAnimalScheduling.getSelectionModel().clearSelection();
        appointmentScheduling.getSelectionModel().clearSelection();
        nameVeterianarianScheduling.getSelectionModel().clearSelection();
        schedulingDate.setValue(null);
        reasonScheduling.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
