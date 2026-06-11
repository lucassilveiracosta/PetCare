package gui.controllers;

import business.controller.ControllerPetCareServer;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.person.Owner;
import gui.Navigator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Pet card (carteirinha) — the attendant picks a pet on the left and sees its
 * data and vaccination card on the right.
 */
public class PetCardController implements Initializable {

    @FXML private ListView<String> listPets;
    @FXML private VBox cardBox;

    private ControllerPetCareServer server;
    private List<Animal> pets;

    private static final DateTimeFormatter D = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        server = ControllerPetCareServer.getInstance();
        listPets.getSelectionModel().selectedIndexProperty().addListener((o, old, nw) -> {
            int i = nw.intValue();
            showCard((i >= 0 && i < pets.size()) ? pets.get(i) : null);
        });
        loadPets();
        showCard(null);
    }

    private void loadPets() {
        pets = server.getAnimal().getAll();
        var items = FXCollections.<String>observableArrayList();
        for (Animal a : pets) {
            String owner = (a instanceof DomesticAnimal da) ? "  ·  " + da.getOwner().getName() : "";
            items.add(a.getName() + "  (" + a.getSpecies() + ")" + owner);
        }
        if (items.isEmpty()) items.add("No pets registered.");
        listPets.setItems(items);
    }

    private void showCard(Animal a) {
        cardBox.getChildren().clear();
        if (a == null) {
            cardBox.getChildren().add(new Label("Select a pet on the left to see its card."));
            return;
        }

        // ── Header ────────────────────────────────────────────────────────────
        Label name = new Label(a.getName());
        name.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label subtitle = new Label(a.getSpecies() + " · " + a.getRace());
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");
        cardBox.getChildren().addAll(name, subtitle, new Separator());

        // ── Animal data ───────────────────────────────────────────────────────
        cardBox.getChildren().add(section("Animal"));
        cardBox.getChildren().add(row("Sex / Size", a.getSex().name() + " · " + a.getSize().name()));
        cardBox.getChildren().add(row("Stage of life", a.getStageOfLife().name()));
        cardBox.getChildren().add(row("Birth date", a.getBirthDate().format(D)));
        cardBox.getChildren().add(row("Weight", a.getWeight() + " kg"));
        if (a instanceof DomesticAnimal da) {
            cardBox.getChildren().add(row("Temperament", da.getTemperament().name()));
            cardBox.getChildren().add(row("Castrated", da.isCastrated() ? "Yes" : "No"));
            Owner o = da.getOwner();
            cardBox.getChildren().add(new Separator());
            cardBox.getChildren().add(section("Owner"));
            cardBox.getChildren().add(row("Name", o.getName()));
            cardBox.getChildren().add(row("CPF", o.getCpf()));
            cardBox.getChildren().add(row("Phone", o.getTelephone()));
        }

        // ── Vaccination card ──────────────────────────────────────────────────
        cardBox.getChildren().add(new Separator());
        cardBox.getChildren().add(section("Vaccination card"));
        cardBox.getChildren().add(rabiesBanner(a));

        List<Vaccine> vaccines = a.getVaccines();
        if (vaccines == null || vaccines.isEmpty()) {
            cardBox.getChildren().add(new Label("No vaccines recorded."));
            return;
        }

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(6);
        for (double w : new double[]{200, 110, 110, 70, 90}) {
            ColumnConstraints c = new ColumnConstraints();
            c.setMinWidth(w);
            grid.getColumnConstraints().add(c);
        }
        addRow(grid, 0, true, "Vaccine", "Applied", "Expires", "Rabies", "Status");
        int r = 1;
        for (Vaccine v : vaccines) {
            boolean expired = v.getExpireVaccineDate().isBefore(LocalDate.now());
            addRow(grid, r++, false,
                    v.getVaccineName(),
                    v.getVaccineDate().format(D),
                    v.getExpireVaccineDate().format(D),
                    v.isRabiesVaccine() ? "Yes" : "No",
                    expired ? "Expired" : "Valid");
        }
        cardBox.getChildren().add(grid);
    }

    private Label rabiesBanner(Animal a) {
        boolean valid = a.getVaccines() != null && a.getVaccines().stream()
                .anyMatch(v -> v.isRabiesVaccine() && !v.getExpireVaccineDate().isBefore(LocalDate.now()));
        Label l = new Label(valid ? "✔ Rabies vaccine up to date" : "✘ No valid rabies vaccine");
        l.setMaxWidth(Double.MAX_VALUE);
        l.setStyle("-fx-padding: 6 10; -fx-background-radius: 6; -fx-font-weight: bold; "
                + (valid ? "-fx-background-color: #d4edda; -fx-text-fill: #155724;"
                         : "-fx-background-color: #f8d7da; -fx-text-fill: #721c24;"));
        return l;
    }

    private Label section(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        VBox.setMargin(l, new Insets(4, 0, 0, 0));
        return l;
    }

    private Label row(String key, String value) {
        Label l = new Label(key + ":   " + value);
        return l;
    }

    private void addRow(GridPane grid, int rowIndex, boolean header, String... cells) {
        for (int i = 0; i < cells.length; i++) {
            Label l = new Label(cells[i]);
            if (header) {
                l.setStyle("-fx-font-weight: bold;");
            } else if (i == cells.length - 1) {
                boolean expired = "Expired".equals(cells[i]);
                l.setStyle("-fx-font-weight: bold; -fx-text-fill: " + (expired ? "#c0392b" : "#1e7e34") + ";");
            }
            grid.add(l, i, rowIndex);
        }
    }

    @FXML
    private void handleRefresh() {
        loadPets();
    }

    @FXML
    private void handleBack() {
        Navigator.navigate("Attendant", "/view/fxml/AttendantMenu.fxml");
    }
}
