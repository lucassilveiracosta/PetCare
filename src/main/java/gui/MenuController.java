package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public void openAgendamento(ActionEvent event) throws IOException {
        MockDataLoader mdl = new MockDataLoader();
        mdl.load();

        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/Scheduling.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), 1000, 600));
        stage.setTitle("Scheduling");
        stage.show();
    }

    @FXML
    public void openConsultas(ActionEvent event) throws IOException {
        MockDataLoader mdl = new MockDataLoader();
        mdl.load();
        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/Consultation.fxml"));
        SplitPane root = new SplitPane();
        loader.setRoot(root);
        loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Consultations");
        stage.show();
    }

    @FXML
    public void openDashboard(ActionEvent event) throws IOException {
        MockDataLoader mdl = new MockDataLoader();
        mdl.load();
        FXMLLoader loader = new FXMLLoader(
                MenuController.class.getResource("/view/fxml/SchedulingDashboard.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), 900, 650));
        stage.setTitle("Scheduling Dashboard");
        stage.show();
    }
}
