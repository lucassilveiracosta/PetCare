package app;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.appointment.Appointment;
import business.model.person.Veterinarian;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class AgendamentoViewController implements Initializable {
    @FXML
    private ChoiceBox<String> nomeTutorAgendamento;

    @FXML
    private ChoiceBox<String> nomeAnimalAgendamento;

    @FXML
    private DatePicker dataAgendamento;

    @FXML
    private TextField motivoAgendamento;

    @FXML
    private Button agendarAgendamento;

    private List<Animal> allAnimals;
    private List<Veterinarian> allVets;

    @FXML
    protected void onAgendarClick(ActionEvent event) {
        int selectedAnimalIndex = nomeAnimalAgendamento.getSelectionModel().getSelectedIndex();
        LocalDate date = dataAgendamento.getValue();
        String motivo = motivoAgendamento.getText();

        if (selectedAnimalIndex < 0 || date == null || motivo == null || motivo.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Campos inválidos", "Por favor, preencha todos os campos.");
            return;
        }

        Animal selectedAnimal = allAnimals.get(selectedAnimalIndex);

        if (allVets.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Não há veterinários cadastrados no sistema.");
            return;
        }

        Veterinarian vet = allVets.get(0);
        LocalDateTime appointmentDateTime = LocalDateTime.of(date, LocalTime.of(8, 0));

        try {
            Appointment appointment = new Appointment(
                    100.0,
                    selectedAnimal,
                    appointmentDateTime,
                    motivo,
                    vet
            );

            ControllerPetCareServer.getInstance().getAppointment().post(appointment);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Consulta agendada com sucesso!");

            nomeAnimalAgendamento.getSelectionModel().clearSelection();
            nomeTutorAgendamento.getSelectionModel().clearSelection();
            dataAgendamento.setValue(null);
            motivoAgendamento.clear();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro ao agendar: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerPetCareServer server = ControllerPetCareServer.getInstance();

        nomeTutorAgendamento.getItems().addAll(
                "João da Silva",
                "Maria Oliveira",
                "Carlos Souza"
        );

        allAnimals = server.getAnimal().getAll();
        for (Animal animal : allAnimals) {
            nomeAnimalAgendamento.getItems().add(animal.getName() + " (" + animal.getSpecies() + ")");
        }

        allVets = server.getPessoa().getAllVets();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
