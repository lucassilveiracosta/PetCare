package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerPerson;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminVeterinariansController implements Initializable {

    @FXML private TableView<Veterinarian> tableVets;
    @FXML private TableColumn<Veterinarian, String> colName, colCrmv, colEmail, colSpecialties;

    @FXML private ListView<Specialty> listSpecialties;
    @FXML private TextField fieldSpecName;
    @FXML private TextArea fieldSpecDescription;
    @FXML private Button btnDeleteVet, btnRemoveSpecialty, btnAddSpecialty;

    private IControllerPerson personCtrl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        personCtrl = ControllerPetCareServer.getInstance().getPessoa();

        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colCrmv.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCrmv()));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colSpecialties.setCellValueFactory(c -> new SimpleStringProperty(summarize(c.getValue())));

        listSpecialties.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Specialty s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setText(null); return; }
                String desc = (s.getDescription() != null && !s.getDescription().isBlank())
                        ? " — " + s.getDescription() : "";
                setText(s.getName() + desc);
            }
        });

        tableVets.getSelectionModel().selectedItemProperty().addListener((obs, old, vet) -> loadSpecialties(vet));

        refresh();
    }

    private String summarize(Veterinarian vet) {
        if (vet.getSpecialties() == null || vet.getSpecialties().isEmpty()) return "-";
        return vet.getSpecialties().stream().map(Specialty::getName).collect(Collectors.joining(", "));
    }

    private void loadSpecialties(Veterinarian vet) {
        boolean none = vet == null;
        btnDeleteVet.setDisable(none);
        btnAddSpecialty.setDisable(none);
        btnRemoveSpecialty.setDisable(none);
        if (none) {
            listSpecialties.getItems().clear();
            return;
        }
        listSpecialties.setItems(FXCollections.observableArrayList(vet.getSpecialties()));
    }

    @FXML
    private void handleAddSpecialty() {
        Veterinarian vet = tableVets.getSelectionModel().getSelectedItem();
        if (vet == null) return;
        try {
            String name = fieldSpecName.getText();
            String desc = fieldSpecDescription.getText();
            if (desc == null || desc.isBlank()) desc = "-";
            vet.getSpecialties().add(new Specialty(name, desc));
            fieldSpecName.clear();
            fieldSpecDescription.clear();
            loadSpecialties(vet);
            tableVets.refresh();
            ControllerPetCareServer.getInstance().saveAll(); // persist to the CSV database
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Specialty", ex.getMessage());
        }
    }

    @FXML
    private void handleRemoveSpecialty() {
        Veterinarian vet = tableVets.getSelectionModel().getSelectedItem();
        Specialty selected = listSpecialties.getSelectionModel().getSelectedItem();
        if (vet == null || selected == null) return;
        vet.getSpecialties().remove(selected);
        loadSpecialties(vet);
        tableVets.refresh();
        ControllerPetCareServer.getInstance().saveAll(); // persist to the CSV database
    }

    @FXML
    private void handleDeleteVet() {
        Veterinarian vet = tableVets.getSelectionModel().getSelectedItem();
        if (vet == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + vet.getName() + "?", ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText(null);
        confirm.showAndWait().filter(b -> b == ButtonType.OK).ifPresent(b -> {
            try {
                personCtrl.delete(vet.getId());
                refresh();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Could not delete", ex.getMessage());
            }
        });
    }

    @FXML
    private void handleRefresh() {
        refresh();
    }

    @FXML
    private void handleProductivity() {
        Navigator.navigate("Productivity", "/view/fxml/VetProductivity.fxml");
    }

    private void refresh() {
        tableVets.setItems(FXCollections.observableArrayList(personCtrl.getAllVets()));
        listSpecialties.getItems().clear();
        btnDeleteVet.setDisable(true);
        btnAddSpecialty.setDisable(true);
        btnRemoveSpecialty.setDisable(true);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
