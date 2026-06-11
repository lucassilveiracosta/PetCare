package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.ServicoPetShop;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Veterinarian;
import enums.PetShopServices;
import enums.ProcedureType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentViewController implements Initializable {

    @FXML private ChoiceBox<String> nameTutorScheduling;
    @FXML private ChoiceBox<String> nameAnimalScheduling;
    @FXML private ChoiceBox<Object> appointmentScheduling; // ProcedureType (medical) or PetShopServices (pet shop)
    @FXML private ChoiceBox<String> nameVeterianarianScheduling; // professional: vet OR employee
    @FXML private Text professionalText;
    @FXML private DatePicker schedulingDate;
    @FXML private ChoiceBox<String> schedulingTime;
    @FXML private TextField reasonScheduling;
    @FXML private Button scheduleScheduling;

    private List<Owner> allOwners = new ArrayList<>();
    private List<Animal> animalsByOwner = new ArrayList<>();
    private List<Veterinarian> allVets = new ArrayList<>();
    private List<Employee> allEmployees = new ArrayList<>();
    private ControllerPetCareServer server;

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        server = ControllerPetCareServer.getInstance();

        // Service types: medical (ProcedureType) + pet shop (PetShopServices)
        appointmentScheduling.getItems().addAll(ProcedureType.values());
        appointmentScheduling.getItems().addAll(PetShopServices.values());
        appointmentScheduling.setConverter(new StringConverter<>() {
            @Override public String toString(Object o) {
                if (o instanceof ProcedureType pt) return pt.getLabel();
                if (o instanceof PetShopServices ps) return petShopLabel(ps);
                return o == null ? "" : o.toString();
            }
            @Override public Object fromString(String s) { return null; }
        });

        allOwners = server.getPessoa().getAllOwners();
        for (Owner o : allOwners) {
            nameTutorScheduling.getItems().add(o.getName());
        }

        nameTutorScheduling.getSelectionModel().selectedIndexProperty().addListener(
                (obs, oldVal, newIdx) -> refreshAnimalList(newIdx.intValue()));

        allVets = server.getPessoa().getAllVets();
        allEmployees = server.getPessoa().getAllEmployees();

        // REQ05: pet shop services are handled by Employees, medical ones by Veterinarians
        appointmentScheduling.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean petShop = newVal instanceof PetShopServices;
            professionalText.setText(petShop ? "Select the Employee:" : "Select the Veterinarian:");
            updateProfessionalList(!petShop);
        });

        updateProfessionalList(true); // default: medical → veterinarians

        // Time slots every 30 min from 07:00 to 19:00
        for (int h = 7; h <= 19; h++) {
            schedulingTime.getItems().add(String.format("%02d:00", h));
            if (h < 19) schedulingTime.getItems().add(String.format("%02d:30", h));
        }
    }

    private void updateProfessionalList(boolean medical) {
        nameVeterianarianScheduling.getItems().clear();
        if (medical) {
            for (Veterinarian v : allVets) nameVeterianarianScheduling.getItems().add(v.getName());
        } else {
            for (Employee e : allEmployees) nameVeterianarianScheduling.getItems().add(e.getName());
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
    public void onBackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/AttendantMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (IOException e) {
            System.out.println("Deu erro ao tentar abrir a tela anterior: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onScheduleClick(ActionEvent event) {
        int ownerIdx   = nameTutorScheduling.getSelectionModel().getSelectedIndex();
        int animalIdx  = nameAnimalScheduling.getSelectionModel().getSelectedIndex();
        int profIdx    = nameVeterianarianScheduling.getSelectionModel().getSelectedIndex();
        LocalDate date = schedulingDate.getValue();
        String reason  = reasonScheduling.getText();
        Object service = appointmentScheduling.getValue();
        String timeStr = schedulingTime.getValue();

        if (ownerIdx < 0 || animalIdx < 0 || profIdx < 0 || date == null || service == null
                || timeStr == null
                || reason == null || reason.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Missing fields",
                    "Please fill in all fields before scheduling.");
            return;
        }

        LocalTime time = LocalTime.parse(timeStr, TIME_FMT);
        Animal animal = animalsByOwner.get(animalIdx);
        Owner owner = allOwners.get(ownerIdx);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        try {
            if (service instanceof PetShopServices ps) {
                // Pet shop service → responsible Employee, stored as an invoice procedure
                Employee employee = allEmployees.get(profIdx);
                String description = petShopLabel(ps) + " — " + reason;
                ServicoPetShop servico = new ServicoPetShop(80.0, animal, dateTime, description, ps, employee);
                ArrayList<Procedure> procedures = new ArrayList<>();
                procedures.add(servico);
                server.getInvoice().post(new Invoice(owner, animal, procedures, new ArrayList<>()));
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Pet shop service (" + petShopLabel(ps) + ") scheduled with " + employee.getName()
                                + " for " + date + " at " + timeStr + ".\nIt will appear in the Financial Dashboard.");
            } else {
                // Medical appointment → responsible Veterinarian
                ProcedureType pt = (ProcedureType) service;
                Veterinarian vet = allVets.get(profIdx);
                String description = pt.getLabel() + " — " + reason;
                server.getAppointment().post(new Appointment(100.0, animal, dateTime, description, vet));
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Appointment scheduled with " + vet.getName() + " for " + date + " at " + timeStr
                                + ".\nIt will appear in the Dashboard.");
            }
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not schedule: " + e.getMessage());
        }
    }

    private String petShopLabel(PetShopServices ps) {
        return switch (ps) {
            case BANHO -> "Bath";
            case TOSA -> "Grooming";
            case BANHO_TOSA -> "Bath & Grooming";
        };
    }

    private void clearForm() {
        nameTutorScheduling.getSelectionModel().clearSelection();
        nameAnimalScheduling.getItems().clear();
        nameAnimalScheduling.getSelectionModel().clearSelection();
        appointmentScheduling.getSelectionModel().clearSelection();
        nameVeterianarianScheduling.getSelectionModel().clearSelection();
        professionalText.setText("Select the Veterinarian:");
        updateProfessionalList(true);
        schedulingDate.setValue(null);
        schedulingTime.getSelectionModel().clearSelection();
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
