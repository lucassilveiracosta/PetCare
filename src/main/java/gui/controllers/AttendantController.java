package gui.controllers;

import business.controller.ControllerPetCareServer;
import gui.Navigator;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.PetShopService;
import business.model.person.Employee;
import business.model.person.Owner;
import enums.PetShopServices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Pet Shop Scheduling — books a pet shop service (bath/grooming) with an Employee
 * as the responsible professional, persisted as a PetShopService in an Invoice.
 */
public class AttendantController implements Initializable {

    @FXML private ChoiceBox<String> nameTutorServices;        // owner
    @FXML private ChoiceBox<String> nameAnimalScheduling;     // animal
    @FXML private ChoiceBox<PetShopServices> nameTutorScheduling2; // service type
    @FXML private ChoiceBox<String> nameTutorScheduling21;    // employee
    @FXML private ChoiceBox<String> nameTutorScheduling11;    // time
    @FXML private DatePicker schedulingDate;
    @FXML private TextField reasonField;
    @FXML private Text professionalText;
    @FXML private Button scheduleScheduling;

    private ControllerPetCareServer server;
    private List<Owner> allOwners = new ArrayList<>();
    private List<Animal> animalsByOwner = new ArrayList<>();
    private List<Employee> allEmployees = new ArrayList<>();

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        server = ControllerPetCareServer.getInstance();

        // Owners
        allOwners = server.getPerson().getAllOwners();
        for (Owner o : allOwners) nameTutorServices.getItems().add(o.getName());
        nameTutorServices.getSelectionModel().selectedIndexProperty().addListener(
                (obs, old, idx) -> refreshAnimals(idx.intValue()));

        // Pet shop service types
        nameTutorScheduling2.getItems().setAll(PetShopServices.values());
        nameTutorScheduling2.setConverter(new StringConverter<>() {
            @Override public String toString(PetShopServices s) { return s == null ? "" : petShopLabel(s); }
            @Override public PetShopServices fromString(String s) { return null; }
        });

        // Employees (pet shop services are done by employees)
        allEmployees = server.getPerson().getAllEmployees();
        for (Employee e : allEmployees) nameTutorScheduling21.getItems().add(e.getName());
        if (professionalText != null) professionalText.setText("Select the Employee:");

        // Time slots 07:00–19:00
        for (int h = 7; h <= 19; h++) {
            nameTutorScheduling11.getItems().add(String.format("%02d:00", h));
            if (h < 19) nameTutorScheduling11.getItems().add(String.format("%02d:30", h));
        }
    }

    private void refreshAnimals(int ownerIndex) {
        nameAnimalScheduling.getItems().clear();
        animalsByOwner.clear();
        if (ownerIndex < 0 || ownerIndex >= allOwners.size()) return;
        Owner owner = allOwners.get(ownerIndex);
        for (Animal a : server.getAnimal().getAll()) {
            if (a instanceof DomesticAnimal da && da.getOwner().getId() == owner.getId()) {
                nameAnimalScheduling.getItems().add(a.getName() + " (" + a.getSpecies() + ")");
                animalsByOwner.add(a);
            }
        }
    }

    @FXML
    public void onScheduleClick(ActionEvent event) {
        int ownerIdx = nameTutorServices.getSelectionModel().getSelectedIndex();
        int animalIdx = nameAnimalScheduling.getSelectionModel().getSelectedIndex();
        int empIdx = nameTutorScheduling21.getSelectionModel().getSelectedIndex();
        PetShopServices service = nameTutorScheduling2.getValue();
        LocalDate date = schedulingDate.getValue();
        String time = nameTutorScheduling11.getValue();
        String reason = reasonField != null ? reasonField.getText() : "";

        if (ownerIdx < 0 || animalIdx < 0 || empIdx < 0 || service == null || date == null || time == null) {
            showAlert(Alert.AlertType.WARNING, "Missing fields", "Please fill in all fields before scheduling.");
            return;
        }

        try {
            Owner owner = allOwners.get(ownerIdx);
            Animal animal = animalsByOwner.get(animalIdx);
            Employee employee = allEmployees.get(empIdx);
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.parse(time, TIME_FMT));
            String description = petShopLabel(service) + (reason != null && !reason.isBlank() ? " — " + reason : "");

            server.getAnimal().validateGroomingAllowed(animal.getId()); // REQ14

            PetShopService petShopService = new PetShopService(80.0, animal, dateTime, description, service, employee);
            ArrayList<Procedure> procedures = new ArrayList<>();
            procedures.add(petShopService);
            server.getInvoice().post(new Invoice(owner, animal, procedures, new ArrayList<>()));

            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Pet shop service (" + petShopLabel(service) + ") scheduled with " + employee.getName()
                            + " for " + date + " at " + time + ".");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not schedule: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameTutorServices.getSelectionModel().clearSelection();
        nameAnimalScheduling.getItems().clear();
        nameTutorScheduling2.getSelectionModel().clearSelection();
        nameTutorScheduling21.getSelectionModel().clearSelection();
        nameTutorScheduling11.getSelectionModel().clearSelection();
        schedulingDate.setValue(null);
        if (reasonField != null) reasonField.clear();
    }

    private String petShopLabel(PetShopServices ps) {
        return switch (ps) {
            case BATH -> "Bath";
            case GROOMING -> "Grooming";
            case BATH_AND_GROOMING -> "Bath & Grooming";
        };
    }

    @FXML
    private void onBackButton(ActionEvent event) {
        Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
